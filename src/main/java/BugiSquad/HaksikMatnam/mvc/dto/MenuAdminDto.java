package BugiSquad.HaksikMatnam.mvc.dto;

import BugiSquad.HaksikMatnam.menu.entity.Menu;
import BugiSquad.HaksikMatnam.order.entity.MenuOrdersItem;

public class MenuAdminDto {
    private Menu menu;
    private int count;
    private int totalPrice;

    public MenuAdminDto(MenuOrdersItem menuOrdersItem) {
        this.menu = menuOrdersItem.getMenu();
        this.count = menuOrdersItem.getCount();
        this.totalPrice = menuOrdersItem.getSumPrice();
    }
}
