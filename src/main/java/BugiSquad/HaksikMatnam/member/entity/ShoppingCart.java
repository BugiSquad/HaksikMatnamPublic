package BugiSquad.HaksikMatnam.member.entity;

import BugiSquad.HaksikMatnam.common.Timestamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ShoppingCart extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "shopping_cart_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private Long menuId;
    private String name;
    private String url;
    private int counts;
    private int price;

    @Builder
    public ShoppingCart(Member member, Long menuId, String name, String url, int counts, int price) {
        this.member = member;
        this.menuId = menuId;
        this.name = name;
        this.url = url;
        this.counts = counts;
        this.price = price;
    }
}
