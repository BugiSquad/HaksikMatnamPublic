package BugiSquad.HaksikMatnam.member.etc;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class ReviewPostDto {

    @NotNull
    @Valid
    @DecimalMin(value = "1.0", message = "평점은 최소 1.0")
    @DecimalMax(value = "5.0", message = "평점은 최대 5.0")
    private double rating;
    @NotNull
    @Valid
    private String text;
    @NotNull
    @Valid
    private Long menuId;
}
