package BugiSquad.HaksikMatnam.mvc.dto;

import BugiSquad.HaksikMatnam.menu.entity.Menu;
import BugiSquad.HaksikMatnam.menu.etc.MenuDto;
import BugiSquad.HaksikMatnam.order.entity.MenuOrdersItem;
import BugiSquad.HaksikMatnam.order.entity.Orders;
import BugiSquad.HaksikMatnam.order.etc.MenuOrdersItemDto;
import BugiSquad.HaksikMatnam.order.etc.OrderType;
import BugiSquad.HaksikMatnam.order.mapper.MenuOrdersItemMapper;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class WaitingOrderDetailDto {
    private Long orderId;
    private int totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private OrderType ordersType;
    private List<MenuOrdersItemAdminDto> menuAdminDto;

    public WaitingOrderDetailDto(Orders orders) {
        this.orderId = orders.getId();
        this.totalPrice = orders.getTotalPrice();
        this.createdAt = orders.getCreatedAt();
        this.modifiedAt = orders.getModifiedAt();
        this.ordersType = orders.getOrderType();
        this.menuAdminDto = orders.getMenuOrdersItemList().stream().map(MenuOrdersItemAdminDto::new).collect(Collectors.toList());
    }
}
