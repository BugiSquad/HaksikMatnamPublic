package BugiSquad.HaksikMatnam.member.mapper;

import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.entity.Review;
import BugiSquad.HaksikMatnam.member.etc.ReviewCompactDto;
import BugiSquad.HaksikMatnam.member.etc.ReviewCompactDtoWithMember;
import BugiSquad.HaksikMatnam.member.etc.ReviewDto;
import BugiSquad.HaksikMatnam.member.etc.ReviewPostDto;
import BugiSquad.HaksikMatnam.menu.entity.Menu;
import BugiSquad.HaksikMatnam.menu.mapper.MenuMapper;

public class ReviewMapper {

    public static ReviewDto toDto(Review review) {

        return new ReviewDto(review.getId(), review.getRating(), review.getText(),
                MemberMapper.toCompactDto(review.getMember()), MenuMapper.toCompactDto(review.getMenu()), review.getModifiedAt());
    }

    public static ReviewCompactDto toCompactDto(Review review) {

        return new ReviewCompactDto(review.getId(), review.getRating(), review.getText(), MenuMapper.toCompactDto(review.getMenu()));
    }

    public static Review toEntityByPost(ReviewPostDto dto, Member member, Menu menu) {

        return new Review(dto.getRating(), dto.getText(), "title", member, menu);
    }

    public static ReviewCompactDtoWithMember toCompactDtoWithMember(Review review) {

        return ReviewCompactDtoWithMember.builder()
                .reviewId(review.getId())
                .rating(review.getRating())
                .text(review.getText())
                .memberCompactDto(MemberMapper.toCompactDto(review.getMember()))
                .build();
    }
}
