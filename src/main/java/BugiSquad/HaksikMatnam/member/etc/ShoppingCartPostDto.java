package BugiSquad.HaksikMatnam.member.etc;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ShoppingCartPostDto {

    @NotNull
    @Valid
    private Long menuId;
    @NotNull
    @Valid
    @Min(value = 1, message = "상품의 개수는 1개 이상이어야 합니다.")
    @Max(value = 1000, message = "상품의 개수가 너무 많습니다. 다시 한번 확인해주세요")
    private int counts;
}
