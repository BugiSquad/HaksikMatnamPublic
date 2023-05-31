package BugiSquad.HaksikMatnam.matching.mapper;

import BugiSquad.HaksikMatnam.matching.dto.PostPromiseDto;
import BugiSquad.HaksikMatnam.matching.entity.NoteRoom;
import BugiSquad.HaksikMatnam.matching.entity.Promise;

public class PromiseMapper {
    public static Promise toEntity(NoteRoom noteRoom, PostPromiseDto dto) {
        return Promise.builder()
                .promisedTime(dto.getPromiseTime())
                .location(dto.getLocation())
                .noteRoom(noteRoom)
                .build();
    }
}
