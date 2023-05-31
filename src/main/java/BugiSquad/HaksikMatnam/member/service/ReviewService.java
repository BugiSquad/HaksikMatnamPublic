package BugiSquad.HaksikMatnam.member.service;

import BugiSquad.HaksikMatnam.common.exception.CustomException;
import BugiSquad.HaksikMatnam.common.exception.ErrorCode;
import BugiSquad.HaksikMatnam.common.response.CountDataResponse;
import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.entity.Review;
import BugiSquad.HaksikMatnam.member.etc.ReviewDto;
import BugiSquad.HaksikMatnam.member.etc.ReviewPostDto;
import BugiSquad.HaksikMatnam.member.etc.ReviewUpdateDto;
import BugiSquad.HaksikMatnam.member.mapper.ReviewMapper;
import BugiSquad.HaksikMatnam.member.repository.MemberRepository;
import BugiSquad.HaksikMatnam.member.repository.ReviewRepository;
import BugiSquad.HaksikMatnam.menu.entity.Menu;
import BugiSquad.HaksikMatnam.menu.repository.MenuRepository;
import BugiSquad.HaksikMatnam.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;
    private final MenuService menuService;

    public ResponseEntity<DataResponse<ReviewDto>> findReview(Long id) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_REVIEW));

        return ResponseEntity.ok(DataResponse.response(200, ReviewMapper.toDto(review)));
    }

    public ResponseEntity<CountDataResponse<List<ReviewDto>>> findReviewsByMember(String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        List<Review> reviewList = reviewRepository.findAllByMember(member);

        return reviewList.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(CountDataResponse.response(200, reviewList.stream().map(ReviewMapper::toDto).toList(), reviewList.size()));
    }

    public ResponseEntity<CountDataResponse<List<ReviewDto>>> findReviewsByMenu(Long menuId) {

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MENU));
        List<Review> reviewList = reviewRepository.findAllByMenu(menu);

        return reviewList.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(CountDataResponse.response(200, reviewList.stream().map(ReviewMapper::toDto).toList(), reviewList.size()));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> postReview(ReviewPostDto reviewPostDto, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        Menu menu = menuRepository.findById(reviewPostDto.getMenuId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MENU));
        Review review = ReviewMapper.toEntityByPost(reviewPostDto, member, menu);
        review.initCreatedAt(LocalDateTime.now());
        reviewRepository.save(review);
        member.getReviewList().add(review);
        menu.getReviewList().add(review);
        menuService.updateTotalRating(menu);

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> updateReview(ReviewUpdateDto reviewUpdateDto, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        Review review = reviewRepository.findById(reviewUpdateDto.getReviewId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_REVIEW));
        if (!review.getMember().equals(member)) {
            throw new CustomException(ErrorCode.INVALID_USER_REVIEW);
        }
        if (reviewUpdateDto.getRating() != null) {
            review.changeRating(reviewUpdateDto.getRating());
            menuService.updateTotalRating(review.getMenu());
        }
        if (reviewUpdateDto.getText() != null) {
            review.changeText(reviewUpdateDto.getText());
        }
        review.changeModifiedAt(LocalDateTime.now());

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> deleteReview(Long id, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_REVIEW));
        if (!review.getMember().equals(member)) {
            throw new CustomException(ErrorCode.INVALID_USER_REVIEW);
        }
        Menu menu = menuRepository.findById(review.getMenu().getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MENU));
        menu.getReviewList().remove(review);
        member.getReviewList().remove(review);
        reviewRepository.delete(review);

        return ResponseEntity.ok(new NoDataResponse(200));
    }
}
