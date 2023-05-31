package BugiSquad.HaksikMatnam.matching.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostPromiseDto{
    private LocalDateTime promiseTime;
    private List<Long> promiseMemberIds;
    private Long noteRoomId;
    private String location;
}
