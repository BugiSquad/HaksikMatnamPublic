package BugiSquad.HaksikMatnam.member.etc;

import lombok.Getter;

@Getter
public class ShoppingCartDto {

    private Long shoppingCartId;
    private Long menuId;
    private String name;
    private String url;
    private int counts;
    private int price;

    public ShoppingCartDto(Long shoppingCartId, Long menuId, String name, String url, int counts, int price) {
        this.shoppingCartId = shoppingCartId;
        this.menuId = menuId;
        this.name = name;
        this.url = url;
        this.counts = counts;
        this.price = price;
    }
}
