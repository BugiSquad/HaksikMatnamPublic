package BugiSquad.HaksikMatnam.order.etc;

import BugiSquad.HaksikMatnam.member.etc.MemberDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrdersPostDto {

    @NotNull
    @Valid
    private String ordersType;
    @NotNull
    @Valid
    private PaymentPostDto paymentPostDto;
    @NotNull
    @Valid
    private List<OrderItemPostDto> menuOrderItems;
}

