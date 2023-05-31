package BugiSquad.HaksikMatnam.order.mapper;

import BugiSquad.HaksikMatnam.order.entity.Orders;
import BugiSquad.HaksikMatnam.order.etc.OrdersDto;

public class OrdersMapper {

    public static OrdersDto toDto(Orders orders) {

        return new OrdersDto(orders.getId(), orders.getTotalPrice(), orders.getOrderType().name(), orders.getMember().getId(),
                PaymentMapper.toDto(orders.getPayment()), orders.getMenuOrdersItemList().stream().map(MenuOrdersItemMapper::toDto).toList(), orders.getModifiedAt());
    }
}
