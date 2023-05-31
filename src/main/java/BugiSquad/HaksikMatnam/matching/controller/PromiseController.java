package BugiSquad.HaksikMatnam.matching.controller;

import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.matching.dto.GetPromiseDto;
import BugiSquad.HaksikMatnam.matching.dto.PostPromiseDto;
import BugiSquad.HaksikMatnam.matching.service.PromiseService;
import BugiSquad.HaksikMatnam.util.Access;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static BugiSquad.HaksikMatnam.util.Access.Token;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/promise")
public class PromiseController {

    private final PromiseService promiseService ;
    private final JwtTokenProvider jwtTokenProvider;

    /** 약속 잡기 **/
    @PostMapping
    public ResponseEntity<NoDataResponse> postPromise(
            @RequestBody PostPromiseDto postPromiseDto,
            @RequestHeader(Token) String token
    ) {
        return promiseService.postPromise(postPromiseDto,jwtTokenProvider.getUserPk(token));
    }

    /** 약속들 조회 **/
    @GetMapping
    public ResponseEntity<DataResponse<List<GetPromiseDto>>> getPromise(
            @RequestHeader(Token) String token
    ) {
        return promiseService.getPromise(jwtTokenProvider.getUserPk(token));
    }
}
