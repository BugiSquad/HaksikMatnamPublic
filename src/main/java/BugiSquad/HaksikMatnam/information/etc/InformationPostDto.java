package BugiSquad.HaksikMatnam.information.etc;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class InformationPostDto {

    @NotNull
    @Valid
    private String title;
    @NotNull
    @Valid
    private String text;
}
