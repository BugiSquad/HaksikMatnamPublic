package BugiSquad.HaksikMatnam.matching.service;

import BugiSquad.HaksikMatnam.common.exception.CustomException;
import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.matching.dto.*;
import BugiSquad.HaksikMatnam.matching.entity.NoteRoom;
import BugiSquad.HaksikMatnam.matching.entity.MemberNoteRoomRelation;
import BugiSquad.HaksikMatnam.matching.entity.Note;
import BugiSquad.HaksikMatnam.matching.entity.Post;
import BugiSquad.HaksikMatnam.matching.etc.GroupType;
import BugiSquad.HaksikMatnam.matching.etc.NoteRoomStatus;
import BugiSquad.HaksikMatnam.matching.mapper.MemberNoteRoomRelationMapper;
import BugiSquad.HaksikMatnam.matching.mapper.NoteMapper;
import BugiSquad.HaksikMatnam.matching.repository.NoteRepository;
import BugiSquad.HaksikMatnam.matching.repository.PromiseRepository;
import BugiSquad.HaksikMatnam.matching.repository.memberNoteRoomRelation.queydslRepository.MemberNoteRoomRelationDetailRepository;
import BugiSquad.HaksikMatnam.matching.repository.noteRoom.NoteRoomRepository;
import BugiSquad.HaksikMatnam.matching.repository.memberNoteRoomRelation.MemberNoteRoomRelationRepository;
import BugiSquad.HaksikMatnam.matching.repository.post.PostRepository;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static BugiSquad.HaksikMatnam.common.exception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoteRoomService {

    private final MemberNoteRoomRelationRepository memberNoteRoomRelationRepository;
    private final MemberNoteRoomRelationDetailRepository memberNoteRoomRelationDetailRepository;
    private final PromiseRepository promiseRepository;
    private final NoteRepository noteRepository;
    private final MemberRepository memberRepository;
    private final NoteRoomRepository noteRoomRepository;
    private final PostRepository postRepository;

    public ResponseEntity<DataResponse<List<GetNoteRoomDto>>> getNoteRooms(String email) {


        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(NOT_EXIST_MEMBER));
        Long memberId = member.getId();


        List<GetNoteRoomDto> getNoteRoomDtos = memberNoteRoomRelationRepository.findByMemberId(memberId).get()
                .stream()
                .map(GetNoteRoomDto::new)
                .collect(Collectors.toList());
//        List<GetNoteRoomDto> getNoteRoomDtos = postRepository.findByMemberId(memberId).get()
//                .stream()
//                .map(GetNoteRoomDto::new)
//                .collect(Collectors.toList());

//        List<GetNoteRoomDto> getNoteRoomDtos = noteRoomRepository.findNoteRoomList(memberId).get();

        return new ResponseEntity<>(DataResponse.response(200, getNoteRoomDtos), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<NoDataResponse> deleteNoteRooms(Long noteRoomId, String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(NOT_EXIST_MEMBER));

        Long memberId = member.getId();
        NoteRoom noteRoom = noteRoomRepository.findById(noteRoomId)
                .orElseThrow(() -> new CustomException(NOT_EXIST_NOTE_ROOM));


        PostNoteDto postNoteDto = new PostNoteDto();
        postNoteDto.setNoteRoomId(noteRoomId);
        postNoteDto.setMessage("퇴장했습니다.");
        postNote(postNoteDto, false,email);

        memberNoteRoomRelationRepository.deleteByMemberIdAndNoteRoomId(memberId, noteRoomId);

        /** 해당 게시글의 주인이라면 게시글 상태 DLETED**/
        Post post = noteRoom.getPost();
        if (post.getMember().equals(member))
            post.changeStatusToDeleted();

        /** 채팅방에서 모두 나갔다면 게시글 물리적 삭제 **/
        //단체 매칭일경우
        if (post.getGroupType().equals(GroupType.ORGANIZATION)) {
            List<MemberNoteRoomRelation> memberNoteRoomRelations = memberNoteRoomRelationRepository.findByNoteRoomId(noteRoomId).get();
            if (memberNoteRoomRelations.size() == 0) {
                promiseRepository.updateNoteRoomIdToNull(noteRoomId);
                noteRepository.deleteByNoteRoomId(noteRoomId);
                noteRoomRepository.deleteByPostId(post.getId());
                postRepository.deleteById(post.getId());
            }
        }

        return new ResponseEntity<>(new NoDataResponse(200), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<DataResponse<Map<String, Object>>> getNotesHistory(Long noteRoomId, String email) {

        Map<String, Object> responseData = new HashMap<>();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(NOT_EXIST_MEMBER));
        Long memberId = member.getId();

        NoteRoom noteRoom = noteRoomRepository.findById(noteRoomId)
                .orElseThrow(() -> new CustomException(NOT_EXIST_NOTE_ROOM));

        if (noteRoom.getStatus().equals(NoteRoomStatus.DELETED))
            throw new CustomException(IS_DELETED_NOTE_ROOM);

        Sort sortCondition = Sort.by(Sort.Direction.ASC, "createdAt");
        List<GetNotesDto> getNotesDtos = noteRepository.findByNoteRoomId(noteRoomId, sortCondition).get()
                .stream()
                .map(GetNotesDto::new)
                .collect(Collectors.toList());


        initNoReadCount(noteRoomId, memberId);
        getNotesDtos.forEach((item) -> {
            if (Objects.equals(item.getMemberId(), memberId))
                item.setMemberId((long) -1);
        });

        responseData.put("groupTitle", noteRoom.getPost().getTitle());
        responseData.put("notes", getNotesDtos);

        return new ResponseEntity<>(DataResponse.response(200, responseData), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<NoDataResponse> postNote(PostNoteDto postNoteDto, boolean firstMessage,String email) {

        Member sender = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(NOT_EXIST_MEMBER));

        Long senderId = sender.getId();
        Long noteRoomId = postNoteDto.getNoteRoomId();
        String message = postNoteDto.getMessage();



        NoteRoom noteRoom = noteRoomRepository.findById(noteRoomId)
                .orElseThrow(() -> new CustomException(NOT_EXIST_NOTE_ROOM));

        Note note = NoteMapper.toEntity(noteRoom, sender, message, firstMessage);
        log.info("is FirstMessage {}", note.isFirstMessage());
        noteRepository.save(note);

        List<MemberNoteRoomRelation> memberNoteRoomRelations = memberNoteRoomRelationRepository.findByNoteRoomId(noteRoomId).get();

        List<MemberNoteRoomRelation> collect = memberNoteRoomRelations.stream()
                .filter(memberNoteRoomRelation -> Objects.equals(memberNoteRoomRelation.getMember().getId(), senderId))
                .peek(memberNoteRoomRelation -> memberNoteRoomRelation.updateLastMessage(message))
                .collect(Collectors.toList());

        List<MemberNoteRoomRelation> collect1 = memberNoteRoomRelations.stream()
                .filter(memberNoteRoomRelation -> !Objects.equals(memberNoteRoomRelation.getMember().getId(), senderId))
                .peek(MemberNoteRoomRelation::plusNoReadCount)
                .collect(Collectors.toList());


        return new ResponseEntity<>(new NoDataResponse(201), HttpStatus.CREATED);
    }


    @Transactional
    public ResponseEntity<DataResponse<Map<String, Object>>> createNoteRoom(
            RequestMatchParticipationDto requestMatchParticipationDto,
            String email) {

        Long postId = requestMatchParticipationDto.getPostId();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(NOT_EXIST_MEMBER));

        Long memberId = member.getId();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(NOT_EXIST_POST));

        GroupType groupType = post.getGroupType();

        /** 그룹 매칭 일경우 **/
        if (groupType.equals(GroupType.ORGANIZATION)) {
            List<NoteRoom> noteRooms = noteRoomRepository.findByPostId(postId)
                    .orElseThrow(() -> new CustomException(NOT_EXIST_NOTE_ROOM));

            NoteRoom noteRoom = noteRooms.get(0);
            Long noteRoomId = noteRoom.getId();

            if (!memberNoteRoomReleationDuplicationValid(noteRoomId, memberId)) {
                return new ResponseEntity<>(DataResponse.response(200, Map.of("noteRoomId", noteRoomId)), HttpStatus.OK);
            }

            MemberNoteRoomRelation memberNoteRoomRelation = MemberNoteRoomRelationMapper.toEntity(member, noteRoom);
            memberNoteRoomRelationRepository.save(memberNoteRoomRelation);

            PostNoteDto postNoteDto = new PostNoteDto();
            postNoteDto.setNoteRoomId(noteRoomId);
            postNoteDto.setMessage(member.getName() + "님이 들어왔습니다.");
            postNote(postNoteDto, true,email);

            return new ResponseEntity<>(DataResponse.response(200, Map.of("noteRoomId", noteRoomId)), HttpStatus.OK);
        } else {
            /** 개인 매칭일 경우 **/
            Member hostMember = post.getMember();
            Long hostMemberId = hostMember.getId();

            if (!myMatchingValid(hostMemberId, memberId)) {
                throw new CustomException(DO_NOT_APPLICATION_MY_MATCHING);
            }

            if (!isFirstMatchingApplication(post.getId(), memberId))
                throw new CustomException(IS_NOT_FIRST_APPLICATION);

            NoteRoom group = NoteRoom.builder()
                    .post(post)
                    .member(member)
                    .build();
            noteRoomRepository.save(group);

            MemberNoteRoomRelation requestMemberNoteRoomRelation = MemberNoteRoomRelationMapper.toEntity(member, group);
            memberNoteRoomRelationRepository.save(requestMemberNoteRoomRelation);

            MemberNoteRoomRelation hostMemberNoteRoomRelation = MemberNoteRoomRelationMapper.toEntity(hostMember, group);
            memberNoteRoomRelationRepository.save(hostMemberNoteRoomRelation);

            return new ResponseEntity<>(DataResponse.response(200, Map.of("noteRoomId", group.getId())), HttpStatus.OK);
        }


    }

    private boolean isFirstMatchingApplication(Long postId, Long memberId) {
        List<NoteRoom> noteRooms = noteRoomRepository.findByPostId(postId).orElse(null);

        if (noteRooms == null)
            return true;

        for (NoteRoom noteRoom : noteRooms) {
            Long noteRoomId = noteRoom.getId();
            List<MemberNoteRoomRelation> memberNoteRoomRelations = memberNoteRoomRelationRepository
                    .findByNoteRoomId(noteRoomId).orElse(null);
            if (memberNoteRoomRelations == null)
                return true;

            for (MemberNoteRoomRelation memberNoteRoomRelation : memberNoteRoomRelations) {
                Long id = memberNoteRoomRelation.getMember().getId();
                if (memberId.equals(id))
                    return false;
            }
        }

        return true;

    }




    /**
     * 나의 게시글에 매칭을 신청 한것인지
     **/
    private boolean myMatchingValid(Long hostMemberId, Long memberId) {
        if (Objects.equals(hostMemberId, memberId))
            return false;
        return true;
    }

    private boolean memberNoteRoomReleationDuplicationValid(Long noteRoomId, Long memberId) {
        Optional<MemberNoteRoomRelation> byGroupsIdAndMemberIdOpt = memberNoteRoomRelationRepository.findByNoteRoomIdAndMemberId(noteRoomId, memberId);
        return byGroupsIdAndMemberIdOpt.isEmpty();
    }

    @Transactional
    public void initNoReadCount(Long noteRoomId, Long memberId) {

        MemberNoteRoomRelation memberNoteRoomRelation = memberNoteRoomRelationRepository.findByNoteRoomIdAndMemberId(noteRoomId, memberId)
                .orElseThrow(() -> new CustomException(NOT_EXIST_MEMBER_NOTE_ROOM));

        memberNoteRoomRelation.initNoReadCount();

    }


    public ResponseEntity<DataResponse<List<AffiliatedMemberDto>>> getAffiliatedMembers(Long noteRoomId, String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(NOT_EXIST_MEMBER));

        List<AffiliatedMemberDto> affiliatedMembers = memberNoteRoomRelationRepository.findByNoteRoomId(noteRoomId).get()
                .stream()
                .filter(f-> !Objects.equals(f.getMember().getId(), member.getId()))
                .map(AffiliatedMemberDto::new)
                .collect(Collectors.toList());


        return new ResponseEntity<>(DataResponse.response(200, affiliatedMembers), HttpStatus.OK);

    }

    public ResponseEntity<DataResponse<List<AffiliatedMemberDto>>> getIndividualNoteRooms(Long postId) {
        List<AffiliatedMemberDto> affiliatedMembers = noteRoomRepository.findByPostId(postId).get()
                .stream()
                .map(note -> {
                    List<MemberNoteRoomRelation> memberNoteRoomRelations = memberNoteRoomRelationRepository.findByNoteRoomId(note.getId()).get();
                    return memberNoteRoomRelations.stream()
                            .map(AffiliatedMemberDto::new)
                            .collect(Collectors.toList())
                            .get(0);
                }).collect(Collectors.toList());

        return new ResponseEntity<>(DataResponse.response(200, affiliatedMembers), HttpStatus.OK);

    }
}
