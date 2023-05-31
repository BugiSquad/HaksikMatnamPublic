package BugiSquad.HaksikMatnam.menu.etc;

import BugiSquad.HaksikMatnam.member.entity.Review;
import BugiSquad.HaksikMatnam.member.etc.MemberDto;
import BugiSquad.HaksikMatnam.member.etc.ReviewCompactDto;
import BugiSquad.HaksikMatnam.member.etc.ReviewCompactDtoWithMember;
import BugiSquad.HaksikMatnam.member.etc.ReviewDto;
import BugiSquad.HaksikMatnam.menu.entity.Menu;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MenuDto {

    private Long menuId;
    private String name;
    private String detail;
    private String imageUrl;
    private int price;
    private double totalRating;
    private String category;
    private List<ReviewCompactDtoWithMember> reviewList;
    private LocalDateTime modifiedAt;

    public MenuDto(Long menuId, String name, String detail, String imageUrl, int price, double totalRating,
                   String category, List<ReviewCompactDtoWithMember> reviewList, LocalDateTime modifiedAt) {
        this.menuId = menuId;
        this.name = name;
        this.detail = detail;
        this.imageUrl = imageUrl;
        this.price = price;
        this.totalRating = totalRating;
        this.category = category;
        this.reviewList = reviewList;
        this.modifiedAt = modifiedAt;
    }
}
