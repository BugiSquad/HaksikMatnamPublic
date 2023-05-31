package BugiSquad.HaksikMatnam.member.controller;

import BugiSquad.HaksikMatnam.common.response.CountDataResponse;
import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.member.etc.MemberDto;
import BugiSquad.HaksikMatnam.member.etc.MemberPostDto;
import BugiSquad.HaksikMatnam.member.etc.MemberUpdateDto;
import BugiSquad.HaksikMatnam.member.etc.ShoppingCartDto;
import BugiSquad.HaksikMatnam.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberApiController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signIn")
    public ResponseEntity<DataResponse<String>> signIn(@Valid @RequestParam @Email String email,
                                                          @Valid @RequestParam @Size(min = 8, max = 100) String password) {
        return memberService.signIn(email, password);
    }

    @PostMapping("/signUp")
    public ResponseEntity<NoDataResponse> postMember(@Valid @RequestBody MemberPostDto memberPostDto) {
        return memberService.signUp(memberPostDto);
    }

    @GetMapping("/cart")
    public ResponseEntity<CountDataResponse<List<ShoppingCartDto>>> getShoppingCart(@RequestHeader("accessToken") String token) {
        return memberService.findShoppingCart(jwtTokenProvider.getUserPk(token));
    }

    @GetMapping
    public ResponseEntity<DataResponse<MemberDto>> getMember(@RequestHeader("accessToken") String token) {
        return memberService.findMember(jwtTokenProvider.getUserPk(token));
    }


    @PatchMapping
    public ResponseEntity<NoDataResponse> updateMember(@RequestBody MemberUpdateDto memberUpdateDto,
                                                       @RequestHeader("accessToken") String token) {
        return memberService.updateMember(memberUpdateDto, jwtTokenProvider.getUserPk(token));
    }

    @DeleteMapping
    public ResponseEntity<NoDataResponse> deleteMember(@RequestHeader("accessToken") String token) {
        return memberService.deleteMember(jwtTokenProvider.getUserPk(token));
    }

    @PostMapping("checkEmail")
    public ResponseEntity<NoDataResponse> checkDuplicateEmail(@RequestParam String email) {
        return memberService.checkEmail(email);
    }

    @PostMapping("checkName")
    public ResponseEntity<NoDataResponse> checkDuplicateName(@RequestParam String name) {
        return memberService.checkName(name);
    }
}
