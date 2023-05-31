package BugiSquad.HaksikMatnam.matching.dto;

import BugiSquad.HaksikMatnam.member.entity.Member;
import lombok.Data;

@Data
public class MemberMeetingDto {
    private Long id;
    private String profileUrl;
    private String name;


    public MemberMeetingDto(Member member) {
        this.id = member.getId();
        this.profileUrl = member.getProfileUrl();
        this.name = member.getName();
    }
}
