package BugiSquad.HaksikMatnam.order.etc;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentPostDto {

    @NotNull
    @Valid
    private String paymentNum;
    @NotNull
    @Valid
    private String detail;
    @NotNull
    @Valid
    private String paymentType;
}
