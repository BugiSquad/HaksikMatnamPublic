package BugiSquad.HaksikMatnam.member.etc;

import BugiSquad.HaksikMatnam.menu.etc.MenuCompactDto;
import BugiSquad.HaksikMatnam.menu.etc.MenuDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewDto {

    private Long reviewId;
    private double rating;
    private String text;
    private MemberCompactDto memberCompactDto;
    private MenuCompactDto menuCompactDto;
    private LocalDateTime modifiedAt;

    public ReviewDto(Long reviewId, double rating, String text, MemberCompactDto memberCompactDto,
                     MenuCompactDto menuCompactDto, LocalDateTime modifiedAt) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.text = text;
        this.memberCompactDto = memberCompactDto;
        this.menuCompactDto = menuCompactDto;
        this.modifiedAt = modifiedAt;
    }
}