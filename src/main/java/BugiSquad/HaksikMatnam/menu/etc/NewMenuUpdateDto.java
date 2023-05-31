package BugiSquad.HaksikMatnam.menu.etc;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
public class NewMenuUpdateDto {

    @NotNull
    @Valid
    private Long newMenuId;
    private String name;
    private String detail;
    private String url;
    private String category;
}
