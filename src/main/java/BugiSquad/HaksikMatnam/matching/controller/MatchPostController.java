package BugiSquad.HaksikMatnam.matching.controller;

import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.matching.dto.GetMyPostDto;
import BugiSquad.HaksikMatnam.matching.dto.GetPostDto;
import BugiSquad.HaksikMatnam.matching.dto.postPostingDto;
import BugiSquad.HaksikMatnam.matching.etc.PostCondition;
import BugiSquad.HaksikMatnam.matching.service.MatchPostService;
import BugiSquad.HaksikMatnam.util.Access;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static BugiSquad.HaksikMatnam.util.Access.Token;
import static org.springframework.security.config.Elements.JWT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/match/post")
public class MatchPostController {

    private final MatchPostService matchPostService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 매칭 게시글 생성
     **/
    @PostMapping
    public ResponseEntity<NoDataResponse> postPost(
            @RequestBody postPostingDto postPostingDto,
            @RequestHeader(Token) String token
    ) {
        return matchPostService.postPosting(postPostingDto,jwtTokenProvider.getUserPk(token));
    }


    /**
     * 매칭 게시글 전체 조회
     **/
    @GetMapping
    public ResponseEntity<DataResponse<Page<GetPostDto>>> getPost(
            @PageableDefault(size = 20,page = 0) Pageable pageable,
            PostCondition postCondition
    ) throws NoSuchFieldException, IllegalAccessException {
        return matchPostService.getPosts(postCondition, pageable);
    }

    /**ㅂ
     * 나의 매칭 게시글 전체 조회
     **/
    @GetMapping("/my")
    public ResponseEntity<DataResponse<List<GetMyPostDto>>> getMyPost(
            @RequestHeader(Token) String token
    ) throws NoSuchFieldException, IllegalAccessException {
        return matchPostService.getMyPosts(jwtTokenProvider.getUserPk(token));
    }
}
