package BugiSquad.HaksikMatnam.matching.dto;

import BugiSquad.HaksikMatnam.matching.entity.MemberNoteRoomRelation;
import BugiSquad.HaksikMatnam.matching.entity.NoteRoom;
import BugiSquad.HaksikMatnam.member.entity.Member;
import lombok.Data;

@Data
public class AffiliatedMemberDto {
    private Long memberId;
    private String profileUrl;
    private String name;
    private String lastMessage;
    private Long noteRoomId;

    public AffiliatedMemberDto(MemberNoteRoomRelation memberNoteRoomRelation) {
        Member member = memberNoteRoomRelation.getMember();
        this.memberId = member.getId();
        this.profileUrl = member.getProfileUrl();
        this.name = member.getName();
        this.lastMessage = memberNoteRoomRelation.getLastMessage();
        this.noteRoomId = memberNoteRoomRelation.getNoteRoom().getId();
    }

    public AffiliatedMemberDto(NoteRoom noteRoom) {
        Member member = noteRoom.getMember();
        this.memberId = member.getId();
        this.profileUrl = member.getProfileUrl();
        this.name = member.getName();
        this.lastMessage = lastMessage;
    }
}
