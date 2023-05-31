package BugiSquad.HaksikMatnam.matching.service;

import BugiSquad.HaksikMatnam.common.exception.CustomException;
import BugiSquad.HaksikMatnam.common.exception.ErrorCode;
import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.matching.dto.GetMyPostDto;
import BugiSquad.HaksikMatnam.matching.dto.GetNoteRoomDto;
import BugiSquad.HaksikMatnam.matching.dto.GetPostDto;
import BugiSquad.HaksikMatnam.matching.dto.postPostingDto;
import BugiSquad.HaksikMatnam.matching.entity.NoteRoom;
import BugiSquad.HaksikMatnam.matching.entity.MemberNoteRoomRelation;
import BugiSquad.HaksikMatnam.matching.entity.Post;
import BugiSquad.HaksikMatnam.matching.etc.GroupType;
import BugiSquad.HaksikMatnam.matching.etc.PostCondition;
import BugiSquad.HaksikMatnam.matching.mapper.MemberNoteRoomRelationMapper;
import BugiSquad.HaksikMatnam.matching.mapper.PostMapper;
import BugiSquad.HaksikMatnam.matching.repository.post.PostRepository;
import BugiSquad.HaksikMatnam.matching.repository.noteRoom.NoteRoomRepository;
import BugiSquad.HaksikMatnam.matching.repository.memberNoteRoomRelation.MemberNoteRoomRelationRepository;
import BugiSquad.HaksikMatnam.matching.repository.post.querydslRepository.PostDetailRepository;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchPostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final NoteRoomRepository noteRoomRepository;
    private final MemberNoteRoomRelationRepository memberNoteRoomRelationRepository;
    private final PostDetailRepository postDetailRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public ResponseEntity<NoDataResponse> postPosting(postPostingDto postPostingDto,String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        Post post = PostMapper.toEntity(postPostingDto,member);
        postRepository.save(post);

        /** 그룹타입 일경우 채팅방 생성**/
        if(post.getGroupType().equals(GroupType.ORGANIZATION)) {
            NoteRoom noteRoom = NoteRoom.builder()
                    .post(post)
                    .member(member)
                    .build();
            noteRoomRepository.save(noteRoom);

            MemberNoteRoomRelation memberNoteRoomRelation = MemberNoteRoomRelationMapper.toEntity(member, noteRoom);
            memberNoteRoomRelationRepository.save(memberNoteRoomRelation);
        }

        return new ResponseEntity<>(new NoDataResponse(201), HttpStatus.CREATED);
    }

    public ResponseEntity<DataResponse<Page<GetPostDto>>> getPosts(PostCondition postCondition, Pageable pageable) throws NoSuchFieldException, IllegalAccessException {
        log.info(postCondition.toString());
        log.info("pagebale.offset {}",pageable.getOffset());
        log.info("pagebale.getPageSize() {}",pageable.getPageSize());

        Page<GetPostDto> allGetPostDtos = postDetailRepository.findAllGetPostDtos(postCondition, pageable);
        return new ResponseEntity<>(new DataResponse<>(200,allGetPostDtos),HttpStatus.OK);
    }

    public ResponseEntity<DataResponse<List<GetMyPostDto>>> getMyPosts(String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        Long memberId = member.getId();


        List<GetMyPostDto> getMyPostDtos = postRepository.findByMemberIdAndGroupTypeNot(memberId, GroupType.GROUP)
                .get()
                .stream()
                .map(GetMyPostDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(DataResponse.response(200,getMyPostDtos),HttpStatus.OK);

    }
}
