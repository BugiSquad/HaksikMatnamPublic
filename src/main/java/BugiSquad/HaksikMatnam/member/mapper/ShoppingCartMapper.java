package BugiSquad.HaksikMatnam.member.mapper;

import BugiSquad.HaksikMatnam.member.entity.ShoppingCart;
import BugiSquad.HaksikMatnam.member.etc.ShoppingCartDto;
import BugiSquad.HaksikMatnam.member.etc.ShoppingCartPostDto;

public class ShoppingCartMapper {

    public static ShoppingCartDto toDto(ShoppingCart shoppingCart) {

        return new ShoppingCartDto(shoppingCart.getId(), shoppingCart.getMenuId(),
                shoppingCart.getName(), shoppingCart.getUrl(), shoppingCart.getCounts(), shoppingCart.getPrice());
    }
}
