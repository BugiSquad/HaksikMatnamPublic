package BugiSquad.HaksikMatnam.message.etc;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SubscriptionPostDto {

    @NotNull
    @Valid
    private String endpoint;
    @NotNull
    @Valid
    private String auth;
    @NotNull
    @Valid
    private String p256dh;
}
