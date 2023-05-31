package BugiSquad.HaksikMatnam.member.etc;

import BugiSquad.HaksikMatnam.matching.dto.MemberNoteRoomRelationDto;
import BugiSquad.HaksikMatnam.matching.entity.MemberNoteRoomRelation;
import BugiSquad.HaksikMatnam.order.etc.OrdersDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MemberDto {

    private Long memberId;
    private String name;
    private String phone;
    private int studentId;
    private String email;
    private String profileUrl;
    private int grade;
    private String gender;
    private String department;
    private InterestDto interestDto;
    private List<ReviewCompactDto> reviewList;
    private List<OrdersDto> ordersList;
    private List<MemberNoteRoomRelationDto> groupList;
    /** 홍석준 MemberGroupRequest -> MemberNoteRoomRelation**/
    private Long voteMenuId;
    private LocalDateTime modifiedAt;

    @Builder
    public MemberDto(Long memberId, String name, String phone, int studentId, String email, String profileUrl, int grade,
                     String gender, String department, InterestDto interestDto, List<ReviewCompactDto> reviewList,
                     List<OrdersDto> ordersList, List<MemberNoteRoomRelationDto> groupList, Long voteMenuId,
                     LocalDateTime modifiedAt) {
        this.memberId = memberId;
        this.name = name;
        this.phone = phone;
        this.studentId = studentId;
        this.email = email;
        this.profileUrl = profileUrl;
        this.grade = grade;
        this.gender = gender;
        this.department = department;
        this.interestDto = interestDto;
        this.reviewList = reviewList;
        this.ordersList = ordersList;
        this.groupList = groupList;
        this.voteMenuId = voteMenuId;
        this.modifiedAt = modifiedAt;
    }
}
