package BugiSquad.HaksikMatnam.matching.dto;

import BugiSquad.HaksikMatnam.common.mapper.StudentIdConverter;
import BugiSquad.HaksikMatnam.common.mapper.MyTimeConverter;
import BugiSquad.HaksikMatnam.matching.entity.Post;
import BugiSquad.HaksikMatnam.matching.etc.GroupType;
import BugiSquad.HaksikMatnam.member.entity.Interest;
import BugiSquad.HaksikMatnam.member.mapper.InterestMapper;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.List;

@Data
public class GetPostDto {

    private Long postId;
    private String title;
    private String body;
    private Long minutesLeftUntilMeal;
    private GroupType groupType;
    private Long memberId;
    private String name;
    private String memberProfileUrl;
    private Integer yearOfEnter;
    private List<String> interest;

    @QueryProjection
    public GetPostDto(Long id, Long memberId, String memberProfileUrl, Post post, Interest interest, String name, Integer studentId) throws IllegalAccessException {
        this.postId = id;
        this.body = post.getBody();
        this.minutesLeftUntilMeal = MyTimeConverter.minutesFromNow(post.getScheduledMealTime());
        this.title = post.getTitle();
        this.memberId = memberId;
        this.name = name;
        this.memberProfileUrl = memberProfileUrl;
        this.yearOfEnter = StudentIdConverter.parseYearOfEnter(studentId);
        this.interest = InterestMapper.onlyTrueAtStringList(interest);
        this.groupType = post.getGroupType();
    }
}
