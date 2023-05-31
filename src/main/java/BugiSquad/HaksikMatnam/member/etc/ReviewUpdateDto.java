package BugiSquad.HaksikMatnam.member.etc;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReviewUpdateDto {

    @NotNull
    @Valid
    private Long reviewId;
    @Valid
    @DecimalMin(value = "1.0", message = "평점은 최소 1.0")
    @DecimalMax(value = "5.0", message = "평점은 최대 5.0")
    private Double rating;
    private String text;
}
