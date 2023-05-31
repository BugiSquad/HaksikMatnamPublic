package BugiSquad.HaksikMatnam.member.etc;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCompactDto {

    private Long memberId;
    private String name;
    private int studentId;
    private String profileUrl;
    private int grade;
    private String gender;
    private String department;
    private InterestDto interestDto;
    private Long voteMenuId;

    public MemberCompactDto(Long memberId, String name, int studentId, String profileUrl, int grade, String gender, String department, InterestDto interestDto, Long voteMenuId) {
        this.memberId = memberId;
        this.name = name;
        this.studentId = studentId;
        this.profileUrl = profileUrl;
        this.grade = grade;
        this.gender = gender;
        this.department = department;
        this.interestDto = interestDto;
        this.voteMenuId = voteMenuId;
    }
}
