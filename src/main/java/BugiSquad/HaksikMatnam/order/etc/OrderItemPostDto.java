package BugiSquad.HaksikMatnam.order.etc;

import BugiSquad.HaksikMatnam.menu.etc.MenuDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class OrderItemPostDto {

    private Long menuId;
    @Valid
    @Min(value = 1, message = "상품의 개수는 1개 이상이어야 합니다")
    @Max(value = 1000, message = "상품의 개수가 너무 많습니다. 다시 확인해주세요")
    private int count;
}
