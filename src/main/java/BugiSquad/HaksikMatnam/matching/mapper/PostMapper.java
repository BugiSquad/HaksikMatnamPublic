package BugiSquad.HaksikMatnam.matching.mapper;

import BugiSquad.HaksikMatnam.matching.dto.PostDto;
import BugiSquad.HaksikMatnam.matching.dto.postPostingDto;
import BugiSquad.HaksikMatnam.matching.entity.Post;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.mapper.MemberMapper;

public class PostMapper {

    public static Post toEntity(postPostingDto dto, Member member) {

        return Post.builder()
                .groupType(dto.getGroupType())
                .title(dto.getTitle())
                .body(dto.getBody())
                .scheduledMealTime(dto.getScheduledMealTime())
                .member(member)
                .build();
    }

    public static PostDto toDto(Post post) {

        return PostDto.builder()
                .post_id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .scheduledMealTime(post.getScheduledMealTime())
                .groupType(post.getGroupType().name())
                .memberCompactDto(MemberMapper.toCompactDto(post.getMember()))
                .status(post.getStatus().name())
                .lastModifiedAt(post.getModifiedAt())
                .build();
    }
}
