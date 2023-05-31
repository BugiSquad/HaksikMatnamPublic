package BugiSquad.HaksikMatnam.information.etc;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class InformationDto {

    private Long informationId;
    private String title;
    private String text;
    private LocalDateTime modifiedAt;

    @Builder
    public InformationDto(Long informationId, String title, String text, LocalDateTime modifiedAt) {
        this.informationId = informationId;
        this.title = title;
        this.text = text;
        this.modifiedAt = modifiedAt;
    }
}
