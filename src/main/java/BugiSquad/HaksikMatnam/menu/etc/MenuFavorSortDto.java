package BugiSquad.HaksikMatnam.menu.etc;

import BugiSquad.HaksikMatnam.member.etc.ReviewCompactDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MenuFavorSortDto {

    private Long menuId;
    private String name;
    private String detail;
    private String imageUrl;
    private int price;
    private double totalRating;
    private String category;
    private List<ReviewCompactDto> reviewList;
    private LocalDateTime modifiedAt;
    private int orderCount;

    @Builder
    public MenuFavorSortDto(Long menuId, String name, String detail, String imageUrl, int price, double totalRating, String category, List<ReviewCompactDto> reviewList, LocalDateTime modifiedAt, int orderCount) {
        this.menuId = menuId;
        this.name = name;
        this.detail = detail;
        this.imageUrl = imageUrl;
        this.price = price;
        this.totalRating = totalRating;
        this.category = category;
        this.reviewList = reviewList;
        this.modifiedAt = modifiedAt;
        this.orderCount = orderCount;
    }
}
