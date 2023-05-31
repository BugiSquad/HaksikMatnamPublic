package BugiSquad.HaksikMatnam.matching.dto;

import BugiSquad.HaksikMatnam.member.etc.MemberCompactDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {

    private Long post_id;
    private String title;
    private String body;
    private LocalDateTime scheduledMealTime;
    private String groupType;
    private MemberCompactDto memberCompactDto;
    private String status;
    private LocalDateTime lastModifiedAt;

    @Builder
    public PostDto(Long post_id, String title, String body, LocalDateTime scheduledMealTime, String groupType, MemberCompactDto memberCompactDto, String status, LocalDateTime lastModifiedAt) {
        this.post_id = post_id;
        this.title = title;
        this.body = body;
        this.scheduledMealTime = scheduledMealTime;
        this.groupType = groupType;
        this.memberCompactDto = memberCompactDto;
        this.status = status;
        this.lastModifiedAt = lastModifiedAt;
    }
}
