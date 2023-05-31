package BugiSquad.HaksikMatnam.matching.dto;

import BugiSquad.HaksikMatnam.matching.entity.NoteRoom;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.etc.MemberCompactDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberNoteRoomRelationDto {


    private Long relation_id;
    private NoteRoomDto noteRoom;
    private Integer noReadCount = 0;
    private String lastMessage;
    private LocalDateTime lastModifiedAt;

    @Builder
    public MemberNoteRoomRelationDto(Long relation_id, NoteRoomDto noteRoom, Integer noReadCount, String lastMessage, LocalDateTime lastModifiedAt) {
        this.relation_id = relation_id;
        this.noteRoom = noteRoom;
        this.noReadCount = noReadCount;
        this.lastMessage = lastMessage;
        this.lastModifiedAt = lastModifiedAt;
    }
}
