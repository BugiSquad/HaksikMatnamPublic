package BugiSquad.HaksikMatnam.member.etc;

import BugiSquad.HaksikMatnam.menu.etc.MenuCompactDto;
import lombok.Getter;

@Getter
public class ReviewCompactDto {

    private Long reviewId;
    private double rating;
    private String text;
    private MenuCompactDto menuCompactDto;

    public ReviewCompactDto(Long reviewId, double rating, String text, MenuCompactDto menuCompactDto) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.text = text;
        this.menuCompactDto = menuCompactDto;
    }
}
