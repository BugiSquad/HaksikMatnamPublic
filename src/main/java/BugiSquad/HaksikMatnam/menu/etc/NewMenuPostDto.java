package BugiSquad.HaksikMatnam.menu.etc;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class NewMenuPostDto {

    @NotNull
    @Valid
    private String name;
    @NotNull
    @Valid
    private String detail;
    @NotNull
    @Valid
    private String url;
    @NotNull
    @Valid
    private String category;


    public NewMenuPostDto(String name, String detail, String url, String category) {
        this.name = name;
        this.detail = detail;
        this.url = url;
        this.category = category;
    }
}
