package BugiSquad.HaksikMatnam.member.service;

import BugiSquad.HaksikMatnam.common.exception.CustomException;
import BugiSquad.HaksikMatnam.common.exception.ErrorCode;
import BugiSquad.HaksikMatnam.common.response.CountDataResponse;
import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.member.entity.Interest;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.etc.*;
import BugiSquad.HaksikMatnam.member.mapper.InterestMapper;
import BugiSquad.HaksikMatnam.member.mapper.MemberMapper;
import BugiSquad.HaksikMatnam.member.mapper.ShoppingCartMapper;
import BugiSquad.HaksikMatnam.member.repository.InterestRepository;
import BugiSquad.HaksikMatnam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final InterestRepository interestRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initAdminResource() {

        LocalDateTime now = LocalDateTime.now();
        Interest interest = new Interest(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        interest.initCreatedAt(now);
        interestRepository.save(interest);
        Member member = new Member("admin", "010-0000-0000", 1, "admin@gmail.com", "admin.com/temp.jpg", 1, Gender.NONE, "관리자", MemberType.ADMIN, interest, encoder.encode("admin"));
        member.initCreatedAt(now);
        memberRepository.save(member);
    }

    public ResponseEntity<DataResponse<MemberDto>> findMember(String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        return ResponseEntity.ok(DataResponse.response(200, MemberMapper.toDto(member)));
    }

    public ResponseEntity<DataResponse<String>> signIn(String email, String password) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        if (!encoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_LOGIN);
        }

        return ResponseEntity.ok(DataResponse.response(200,
                jwtTokenProvider.createToken(member.getEmail(), member.getMemberType().name())));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> signUp(MemberPostDto memberPostDto) {

        if (memberPostDto.getInterestPostDto() == null) {
            throw new CustomException(ErrorCode.INVALID_FIELD);
        }
        LocalDateTime now = LocalDateTime.now();
        Interest interest = InterestMapper.toEntityByPost(memberPostDto.getInterestPostDto());
        interest.initCreatedAt(now);
        interestRepository.save(interest);
        Member member = MemberMapper.toEntityByPost(memberPostDto, interest, encoder.encode(memberPostDto.getPassword()));
        member.initCreatedAt(now);
        memberRepository.save(member);

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    public ResponseEntity<CountDataResponse<List<ShoppingCartDto>>> findShoppingCart(String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        return ResponseEntity.ok(CountDataResponse.response(200,
                member.getShoppingCartList().stream().map(ShoppingCartMapper::toDto).toList(), member.getShoppingCartList().size()));

    }

    @Transactional
    public ResponseEntity<NoDataResponse> updateMember(MemberUpdateDto memberUpdateDto, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        if (memberUpdateDto.getPhone() != null) {
            member.changePhone(memberUpdateDto.getPhone());
        }
        if (memberUpdateDto.getProfileUrl() != null) {
            member.changeProfileUrl(memberUpdateDto.getProfileUrl());
        }
        if (memberUpdateDto.getGrade() != null) {
            member.changeGrade(memberUpdateDto.getGrade());
        }
        if (memberUpdateDto.getDepartment() != null) {
            member.changeDepartment(memberUpdateDto.getDepartment());
        }
        member.changeModifiedAt(LocalDateTime.now());

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> deleteMember(String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        memberRepository.delete(member);

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    public ResponseEntity<NoDataResponse> checkEmail(String email) {

        Optional<Member> findMember = memberRepository.findByEmail(email);
        if (findMember.isEmpty()) {
            return ResponseEntity.ok(new NoDataResponse(200));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<NoDataResponse> checkName(String name) {

        Optional<Member> findMember = memberRepository.findByName(name);
        if (findMember.isEmpty()) {
            return ResponseEntity.ok(new NoDataResponse(200));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
