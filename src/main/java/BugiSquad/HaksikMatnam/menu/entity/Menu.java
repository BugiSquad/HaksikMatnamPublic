package BugiSquad.HaksikMatnam.menu.entity;

import BugiSquad.HaksikMatnam.common.Timestamped;
import BugiSquad.HaksikMatnam.member.entity.Review;
import BugiSquad.HaksikMatnam.menu.etc.MenuCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Menu extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "menu_id")
    private Long id;
    private String name;
    private String detail;
    private String imageUrl;
    private int price;
    private double totalRating;
    @Enumerated(EnumType.STRING)
    private MenuCategory category;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.REMOVE)
    private List<Review> reviewList = new ArrayList<>();
    @OneToMany(mappedBy = "menu",cascade = CascadeType.REMOVE)
    private List<MenuFavor> countList = new ArrayList<>();

    public Menu changeName(String name) {
        this.name = name;
        return this;
    }

    public Menu changeDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public Menu changeImageUrl(String url) {
        this.imageUrl = url;
        return this;
    }

    public Menu changePrice(int price) {
        this.price = price;
        return this;
    }

    public Menu changeTotalRating(double rating) {
        this.totalRating = rating;
        return this;
    }

    public Menu changeMenuCategory(MenuCategory menuCategory) {
        this.category = menuCategory;
        return this;
    }

    public Menu(String name, String detail, String imageUrl, int price, double totalRating, MenuCategory category) {
        this.name = name;
        this.detail = detail;
        this.imageUrl = imageUrl;
        this.price = price;
        this.totalRating = totalRating;
        this.category = category;
    }
}
