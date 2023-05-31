package BugiSquad.HaksikMatnam.order.mapper;

import BugiSquad.HaksikMatnam.order.entity.Payment;
import BugiSquad.HaksikMatnam.order.etc.PaymentDto;
import BugiSquad.HaksikMatnam.order.etc.PaymentPostDto;
import BugiSquad.HaksikMatnam.order.etc.PaymentType;

import java.time.LocalDateTime;

public class PaymentMapper {

    public static PaymentDto toDto(Payment payment) {

        return new PaymentDto(payment.getId(), payment.getPaymentNum(), payment.getPaymentTime(),
                payment.getConfirmNum(), payment.getDetail(), payment.getPaymentType().name(), payment.getModifiedAt());
    }

    public static Payment toEntityByPost(PaymentPostDto paymentPostDto, LocalDateTime now, String confirmNum) {

        return new Payment(paymentPostDto.getPaymentNum(), now, confirmNum,
                paymentPostDto.getDetail(), PaymentType.valueOf(paymentPostDto.getPaymentType()));
    }
}
