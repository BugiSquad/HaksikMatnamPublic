package BugiSquad.HaksikMatnam.mvc.dto;

import BugiSquad.HaksikMatnam.menu.entity.Menu;
import BugiSquad.HaksikMatnam.menu.etc.MenuDto;
import BugiSquad.HaksikMatnam.menu.mapper.MenuMapper;
import BugiSquad.HaksikMatnam.order.entity.MenuOrdersItem;
import BugiSquad.HaksikMatnam.order.entity.Orders;
import lombok.Data;

@Data
public class MenuOrdersItemAdminDto {
    private int count;
    private MenuDto menu;

    public MenuOrdersItemAdminDto(MenuOrdersItem entity) {
        this.count = entity.getCount();
        this.menu = MenuMapper.toDto(entity.getMenu());
    }
}
