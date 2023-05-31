package BugiSquad.HaksikMatnam.matching.service;

import BugiSquad.HaksikMatnam.common.exception.CustomException;
import BugiSquad.HaksikMatnam.common.exception.ErrorCode;
import BugiSquad.HaksikMatnam.common.mapper.MyTimeConverter;
import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.matching.dto.GetPromiseDto;
import BugiSquad.HaksikMatnam.matching.dto.MemberMeetingDto;
import BugiSquad.HaksikMatnam.matching.dto.PostNoteDto;
import BugiSquad.HaksikMatnam.matching.dto.PostPromiseDto;
import BugiSquad.HaksikMatnam.matching.entity.NoteRoom;
import BugiSquad.HaksikMatnam.matching.entity.Post;
import BugiSquad.HaksikMatnam.matching.entity.Promise;
import BugiSquad.HaksikMatnam.matching.entity.PromiseMemberRelation;
import BugiSquad.HaksikMatnam.matching.mapper.PromiseMapper;
import BugiSquad.HaksikMatnam.matching.mapper.PromiseMemberRelationMapper;
import BugiSquad.HaksikMatnam.matching.repository.PromiseMemberRelation.PromiseMemberRelationRepository;
import BugiSquad.HaksikMatnam.matching.repository.PromiseRepository;
import BugiSquad.HaksikMatnam.matching.repository.noteRoom.NoteRoomRepository;
import BugiSquad.HaksikMatnam.matching.repository.post.PostRepository;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromiseService {

    private final NoteRoomRepository noteRoomRepository;
    private final MemberRepository memberRepository;
    private final PromiseRepository promiseRepository;
    private final PostRepository postRepository;
    private final PromiseMemberRelationRepository promiseMemberRelationRepository;
    private final NoteRoomService noteRoomService;

    @Transactional
    public ResponseEntity<NoDataResponse> postPromise(PostPromiseDto postPromiseDto,String email) {

        Member reqMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));


        Long noteRoomId = postPromiseDto.getNoteRoomId();
        NoteRoom noteRoom = noteRoomRepository.findById(noteRoomId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_NOTE_ROOM));

        Promise promise = PromiseMapper.toEntity(noteRoom, postPromiseDto);
        promiseRepository.save(promise);

        List<String> promiseMemberNames = new ArrayList<>();
        List<PromiseMemberRelation> promiseMemberRelationList = new ArrayList<>();

        List<Long> targetMemberIds = postPromiseDto.getPromiseMemberIds();
        Long memberId = reqMember.getId();
        targetMemberIds.add(memberId);
        for (Long promiseMemberId : targetMemberIds) {
            Member member = memberRepository.findById(promiseMemberId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
            PromiseMemberRelation promiseMemberRelation = PromiseMemberRelationMapper.toEntity(member, promise);
            promiseMemberRelationList.add(promiseMemberRelation);

            promiseMemberNames.add(member.getName());
        }
        promiseMemberRelationRepository.saveAll(promiseMemberRelationList);

        Post post = noteRoom.getPost();
        post.changeToGroup();

        PostNoteDto postNoteDto = new PostNoteDto();

        postNoteDto.setNoteRoomId(noteRoomId);
        StringBuilder message = new StringBuilder("[학식맛남] ");

        for (int i = 0; i < promiseMemberNames.size() ; i++) {
            message.append(promiseMemberNames.get(i)+"님");
            if(i+1 < promiseMemberNames.size())
                message.append(",");
        }
        message.append("과 "+ promise.getLocation() +"에서 "+ MyTimeConverter.siBunFormat(promise.getPromisedTime())+" 약속이 성사되었습니다.");
        postNoteDto.setMessage(message.toString());

        noteRoomService.postNote(postNoteDto, false,email);


        return new ResponseEntity<>(new NoDataResponse(201), HttpStatus.CREATED);
    }

    public ResponseEntity<DataResponse<List<GetPromiseDto>>> getPromise(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        Long memberId = member.getId();

        List<GetPromiseDto> getPromiseDtos = promiseMemberRelationRepository.findByMemberId(memberId)
                .get()
                .stream()
                .map(GetPromiseDto::new)
                .collect(Collectors.toList());

        for (GetPromiseDto getPromiseDto : getPromiseDtos) {
            List<MemberMeetingDto> memberMeetingDtos = promiseMemberRelationRepository.findByPromiseId(getPromiseDto.getPromiseId())
                    .get()
                    .stream()
                    .map(m -> new MemberMeetingDto(m.getMember()))
                    .collect(Collectors.toList());
            getPromiseDto.setMemberMeetingDto(memberMeetingDtos);
        }

        getPromiseDtos.sort(Comparator.comparing(GetPromiseDto::getPromisedTime).reversed());

        return new ResponseEntity<>(DataResponse.response(200,getPromiseDtos),HttpStatus.OK);
    }
}
