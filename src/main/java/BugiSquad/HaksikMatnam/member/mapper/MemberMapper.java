package BugiSquad.HaksikMatnam.member.mapper;

import BugiSquad.HaksikMatnam.matching.mapper.MemberNoteRoomRelationMapper;
import BugiSquad.HaksikMatnam.member.entity.Interest;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.etc.*;
import BugiSquad.HaksikMatnam.order.mapper.OrdersMapper;

import java.util.stream.Collectors;

public class MemberMapper {

    public static MemberDto toDto(Member member) {

        return MemberDto.builder()
                .memberId(member.getId())
                .name(member.getName())
                .phone(member.getPhone())
                .studentId(member.getStudentId())
                .email(member.getEmail())
                .profileUrl(member.getProfileUrl())
                .grade(member.getGrade())
                .gender(member.getGender().name())
                .department(member.getDepartment())
                .interestDto(InterestMapper.toDto(member.getInterest()))
                .reviewList(member.getReviewList().stream().map(ReviewMapper::toCompactDto).toList())
                .ordersList(member.getOrdersList().stream().map(OrdersMapper::toDto).toList())
                .groupList(member.getGroupList().stream().map(MemberNoteRoomRelationMapper::toDto).toList())
                .voteMenuId(member.getVoteMenuId())
                .modifiedAt(member.getModifiedAt())
                .build();
    }

    public static MemberCompactDto toCompactDto(Member member) {

        return new MemberCompactDto(
                member.getId(),
                member.getName(),
                member.getStudentId(),
                member.getProfileUrl(),
                member.getGrade(),
                member.getGender().name(),
                member.getDepartment(),
                InterestMapper.toDto(member.getInterest()),
                member.getVoteMenuId()
        );
    }

    public static Member toEntityByPost(MemberPostDto dto, Interest interest, String password) {

        return new Member(dto.getName(), dto.getPhone(), dto.getStudentId(), dto.getEmail(), dto.getProfileUrl(),
                dto.getGrade(), Gender.valueOf(dto.getGender()), dto.getDepartment(), MemberType.STUDENT,
                interest, password);
    }
}
