package BugiSquad.HaksikMatnam.menu.etc;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
public class MenuUpdateDto {

    @NotNull
    @Valid
    private Long menuId;
    private String name;
    private String detail;
    private String imageUrl;
    @Valid
    @Min(value = 1, message = "가격은 최소 1 이상이어야 합니다.")
    @Max(value = 1000000, message = "가격은 1000000원 이하여야합니다. 다시 한번 확인해주세요.")
    private Integer price;
    private String category;
}
