package BugiSquad.HaksikMatnam.member.controller;

import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.member.etc.InterestUpdateDto;
import BugiSquad.HaksikMatnam.member.service.InterestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interest")
public class InterestApiController {

    private final InterestService interestService;
    private final JwtTokenProvider jwtTokenProvider;

    @PatchMapping
    public ResponseEntity<NoDataResponse> updateInterest(@RequestBody InterestUpdateDto interestUpdateDto,
                                                         @RequestHeader("accessToken") String token) {
        return interestService.updateInterest(interestUpdateDto, jwtTokenProvider.getUserPk(token));
    }
}
