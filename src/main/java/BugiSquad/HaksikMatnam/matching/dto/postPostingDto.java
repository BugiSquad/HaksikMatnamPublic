package BugiSquad.HaksikMatnam.matching.dto;
import BugiSquad.HaksikMatnam.matching.etc.GroupType;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class postPostingDto {
    private String title;
    private String body;
    private GroupType groupType;
    private LocalDateTime scheduledMealTime;
}
