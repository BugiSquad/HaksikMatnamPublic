package BugiSquad.HaksikMatnam.menu.etc;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CheckVoteDto {

    private Long menuId;
    private LocalDateTime voteTime;
    private boolean availableToVote;

    @Builder
    public CheckVoteDto(Long menuId, LocalDateTime voteTime, boolean availableToVote) {
        this.menuId = menuId;
        this.voteTime = voteTime;
        this.availableToVote = availableToVote;
    }
}
