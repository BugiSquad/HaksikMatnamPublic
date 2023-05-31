package BugiSquad.HaksikMatnam.order.mapper;

import BugiSquad.HaksikMatnam.menu.mapper.MenuMapper;
import BugiSquad.HaksikMatnam.order.entity.MenuOrdersItem;
import BugiSquad.HaksikMatnam.order.etc.MenuOrdersItemDto;
import BugiSquad.HaksikMatnam.order.etc.OrderItemPostDto;

public class MenuOrdersItemMapper {

    public static MenuOrdersItemDto toDto(MenuOrdersItem menuOrdersItem) {

        return new MenuOrdersItemDto(menuOrdersItem.getId(), menuOrdersItem.getCount(), menuOrdersItem.getSumPrice(),
                menuOrdersItem.getOrders().getId(), menuOrdersItem.getMenu().getId());
    }
}
