package BugiSquad.HaksikMatnam.member.service;

import BugiSquad.HaksikMatnam.common.exception.CustomException;
import BugiSquad.HaksikMatnam.common.exception.ErrorCode;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.member.entity.Interest;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.etc.InterestUpdateDto;
import BugiSquad.HaksikMatnam.member.repository.InterestRepository;
import BugiSquad.HaksikMatnam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<NoDataResponse> updateInterest(InterestUpdateDto interestUpdateDto, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        Interest interest = interestRepository.findById(member.getInterest().getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        if (interestUpdateDto.isPubg() != interest.getPubg()) {
            interest.changePubg(interestUpdateDto.isPubg());
        }
        if (interestUpdateDto.isLol() != interest.getLol()) {
            interest.changeLol(interestUpdateDto.isLol());
        }
        if (interestUpdateDto.isCelebrity() != interest.getCelebrity()) {
            interest.changeCelebrity(interestUpdateDto.isCelebrity());
        }
        if (interestUpdateDto.isCoffee() != interest.getCoffee()) {
            interest.changeCoffee(interestUpdateDto.isCoffee());
        }
        if (interestUpdateDto.isDessert() != interest.getDessert()) {
            interest.changeDessert(interestUpdateDto.isDessert());
        }
        if (interestUpdateDto.isGame() != interest.getGame()) {
            interest.changeGame(interestUpdateDto.isGame());
        }
        if (interestUpdateDto.isPopSong() != interest.getKPop()) {
            interest.changePopSong(interestUpdateDto.isPopSong());
        }
        if (interestUpdateDto.isKPop() != interest.getKPop()) {
            interest.changeKPop(interestUpdateDto.isKPop());
        }
        if (interestUpdateDto.isJPop() != interest.getJPop()) {
            interest.changeJPop(interestUpdateDto.isJPop());
        }
        if (interestUpdateDto.isDrama() != interest.getDrama()) {
            interest.changeDrama(interestUpdateDto.isDrama());
        }
        if (interestUpdateDto.isMovie() != interest.getMovie()) {
            interest.changeMovie(interestUpdateDto.isMovie());
        }
        if (interestUpdateDto.isTravel() != interest.getTravel()) {
            interest.changeTravel(interestUpdateDto.isTravel());
        }
        if (interestUpdateDto.isStudy() != interest.getStudy()) {
            interest.changeStudy(interestUpdateDto.isStudy());
        }
        if (interestUpdateDto.isHiking() != interest.getHiking()) {
            interest.changeHiking(interestUpdateDto.isHiking());
        }
        if (interestUpdateDto.isBook() != interest.getBook()) {
            interest.changeBook(interestUpdateDto.isBook());
        }
        interest.changeModifiedAt(LocalDateTime.now());

        return ResponseEntity.ok(new NoDataResponse(200));
    }
}
