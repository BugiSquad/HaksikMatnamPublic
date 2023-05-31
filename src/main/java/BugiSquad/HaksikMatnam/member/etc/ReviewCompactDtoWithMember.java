package BugiSquad.HaksikMatnam.member.etc;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewCompactDtoWithMember {

    private Long reviewId;
    private double rating;
    private String text;
    private MemberCompactDto memberCompactDto;

    @Builder
    public ReviewCompactDtoWithMember(Long reviewId, double rating, String text, MemberCompactDto memberCompactDto) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.text = text;
        this.memberCompactDto = memberCompactDto;
    }
}
