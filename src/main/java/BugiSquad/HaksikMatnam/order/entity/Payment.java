package BugiSquad.HaksikMatnam.order.entity;

import BugiSquad.HaksikMatnam.common.Timestamped;
import BugiSquad.HaksikMatnam.order.etc.PaymentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Payment extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "payment_id")
    private Long id;
    private String paymentNum;
    private LocalDateTime paymentTime;
    private String confirmNum;
    @Column(name = "payment_detail")
    private String detail;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    public Payment(String paymentNum, LocalDateTime paymentTime, String confirmNum, String detail, PaymentType paymentType) {
        this.paymentNum = paymentNum;
        this.paymentTime = paymentTime;
        this.confirmNum = confirmNum;
        this.detail = detail;
        this.paymentType = paymentType;
    }
}
