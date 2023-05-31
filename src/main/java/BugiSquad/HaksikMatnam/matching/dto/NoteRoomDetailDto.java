package BugiSquad.HaksikMatnam.matching.dto;

import BugiSquad.HaksikMatnam.matching.entity.MemberNoteRoomRelation;
import BugiSquad.HaksikMatnam.matching.entity.NoteRoom;
import lombok.Data;

@Data
public class NoteRoomDetailDto {
    private Long memberNoteRoomRelationId;
    private Long noteRoomId;
    private Integer noReadCount;

    public NoteRoomDetailDto(MemberNoteRoomRelation entity) {
        NoteRoom noteRoom = entity.getNoteRoom();
        this.memberNoteRoomRelationId = entity.getId();
        this.noteRoomId = noteRoom.getId();
        this.noReadCount = entity.getNoReadCount();
    }
}
