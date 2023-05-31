package BugiSquad.HaksikMatnam.matching.dto;

import BugiSquad.HaksikMatnam.common.mapper.MyTimeConverter;
import BugiSquad.HaksikMatnam.matching.entity.Post;
import BugiSquad.HaksikMatnam.matching.etc.GroupType;
import lombok.Data;

@Data
public class GetMyPostDto {

    private Long postId;
    private String title;
    private String body;
    private Long minutesLeftUntilMeal;
    private GroupType groupType;

    public GetMyPostDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.body = post.getBody();
        this.minutesLeftUntilMeal = MyTimeConverter.minutesFromNow(post.getScheduledMealTime());;
        this.groupType = post.getGroupType();
    }
}
