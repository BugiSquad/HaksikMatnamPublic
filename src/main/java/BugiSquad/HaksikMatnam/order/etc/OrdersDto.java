package BugiSquad.HaksikMatnam.order.etc;

import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.etc.MemberDto;
import BugiSquad.HaksikMatnam.order.entity.MenuOrdersItem;
import BugiSquad.HaksikMatnam.order.entity.Orders;
import BugiSquad.HaksikMatnam.order.entity.Payment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrdersDto {

    private Long ordersId;
    private int totalPrice;
    private String ordersType;
    private Long memberId;
    private PaymentDto paymentDto;
    private List<MenuOrdersItemDto> menuOrdersItemDtoList;
    private LocalDateTime modifiedAt;

    public OrdersDto(Long ordersId, int totalPrice, String ordersType, Long memberId, PaymentDto paymentDto,
                     List<MenuOrdersItemDto> menuOrdersItemDtoList, LocalDateTime modifiedAt) {
        this.ordersId = ordersId;
        this.totalPrice = totalPrice;
        this.ordersType = ordersType;
        this.memberId = memberId;
        this.paymentDto = paymentDto;
        this.menuOrdersItemDtoList = menuOrdersItemDtoList;
        this.modifiedAt = modifiedAt;
    }
}
