package BugiSquad.HaksikMatnam.order.etc;

import BugiSquad.HaksikMatnam.order.entity.Payment;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class PaymentDto {

    private Long paymentId;
    private String paymentNum;
    private LocalDateTime paymentTime;
    private String confirmNum;
    private String detail;
    private String paymentType;
    private LocalDateTime modifiedAt;

    public PaymentDto(Long paymentId, String paymentNum, LocalDateTime paymentTime, String confirmNum, String detail,
                      String paymentType, LocalDateTime modifiedAt) {
        this.paymentId = paymentId;
        this.paymentNum = paymentNum;
        this.paymentTime = paymentTime;
        this.confirmNum = confirmNum;
        this.detail = detail;
        this.paymentType = paymentType;
        this.modifiedAt = modifiedAt;
    }
}
