package BugiSquad.HaksikMatnam.matching.mapper;

import BugiSquad.HaksikMatnam.matching.dto.NoteRoomDto;
import BugiSquad.HaksikMatnam.matching.entity.NoteRoom;

public class NoteRoomMapper {

    public static NoteRoomDto toDto(NoteRoom noteRoom) {

        return NoteRoomDto.builder()
                .noteRoom_id(noteRoom.getId())
                .post(PostMapper.toDto(noteRoom.getPost()))
                .status(noteRoom.getStatus().name())
                .lastModifiedAt(noteRoom.getModifiedAt())
                .build();
    }
}
