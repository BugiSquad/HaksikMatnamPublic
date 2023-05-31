package BugiSquad.HaksikMatnam.information.etc;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class InformationUpdateDto {

    @NotNull
    @Valid
    private Long informationId;
    private String title;
    private String text;
}
