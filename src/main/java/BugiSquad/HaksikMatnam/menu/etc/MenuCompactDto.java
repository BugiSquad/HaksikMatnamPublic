package BugiSquad.HaksikMatnam.menu.etc;

import lombok.Getter;

@Getter
public class MenuCompactDto {

    private Long menuId;
    private String name;
    private String detail;
    private String imageUrl;
    private int price;
    private double totalRating;
    private String category;

    public MenuCompactDto(Long menuId, String name, String detail, String imageUrl, int price, double totalRating, String category) {
        this.menuId = menuId;
        this.name = name;
        this.detail = detail;
        this.imageUrl = imageUrl;
        this.price = price;
        this.totalRating = totalRating;
        this.category = category;
    }
}
