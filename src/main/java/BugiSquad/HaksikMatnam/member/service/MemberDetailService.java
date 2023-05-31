package BugiSquad.HaksikMatnam.member.service;


import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String claims) throws UsernameNotFoundException {
        return memberRepository.findByEmail(String.valueOf(claims)).get();
    }


    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }
}

