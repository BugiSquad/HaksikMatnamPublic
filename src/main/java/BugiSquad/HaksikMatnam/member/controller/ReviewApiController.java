package BugiSquad.HaksikMatnam.member.controller;

import BugiSquad.HaksikMatnam.common.response.CountDataResponse;
import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.member.etc.ReviewDto;
import BugiSquad.HaksikMatnam.member.etc.ReviewPostDto;
import BugiSquad.HaksikMatnam.member.etc.ReviewUpdateDto;
import BugiSquad.HaksikMatnam.member.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewApiController {

    private final ReviewService reviewService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<DataResponse<ReviewDto>> getReview(@RequestParam Long id) {
        return reviewService.findReview(id);
    }

    @GetMapping("/member")
    public ResponseEntity<CountDataResponse<List<ReviewDto>>> getReviewsByMember(@RequestHeader("accessToken") String token) {
        return reviewService.findReviewsByMember(jwtTokenProvider.getUserPk(token));
    }

    @GetMapping("/menu")
    public ResponseEntity<CountDataResponse<List<ReviewDto>>> getReviewsByMenu(@RequestParam Long id) {
        return reviewService.findReviewsByMenu(id);
    }

    @PostMapping
    public ResponseEntity<NoDataResponse> postReview(@Valid @RequestBody ReviewPostDto reviewPostDto,
                                                     @RequestHeader("accessToken") String token) {
        return reviewService.postReview(reviewPostDto, jwtTokenProvider.getUserPk(token));
    }

    @PatchMapping
    public ResponseEntity<NoDataResponse> updateReview(@Valid @RequestBody ReviewUpdateDto reviewUpdateDto,
                                                       @RequestHeader("accessToken") String token) {
        return reviewService.updateReview(reviewUpdateDto, jwtTokenProvider.getUserPk(token));
    }

    @DeleteMapping
    public ResponseEntity<NoDataResponse> deleteReview(@RequestParam Long id,
                                                       @RequestHeader("accessToken") String token) {
        return reviewService.deleteReview(id, jwtTokenProvider.getUserPk(token));
    }
}
