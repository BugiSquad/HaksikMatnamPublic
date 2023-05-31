package BugiSquad.HaksikMatnam.menu.etc;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuPostDto {

    @NotNull
    @Valid
    private String name;
    @NotNull
    @Valid
    private String detail;
    @NotNull
    @Valid
    private String imageUrl;
    @NotNull
    @Valid
    @Min(value = 1, message = "가격은 최소 1 이상이어야 합니다.")
    @Max(value = 1000000, message = "가격은 1000000원 이하여야합니다. 다시 한번 확인해주세요.")
    private int price;
    @NotNull
    @Valid
    private String category;


    public MenuPostDto(String name, String detail, String imageUrl, int price, String category) {
        this.name = name;
        this.detail = detail;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }
}
