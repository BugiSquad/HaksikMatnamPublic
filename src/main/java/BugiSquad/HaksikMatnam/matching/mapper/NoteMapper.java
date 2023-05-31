package BugiSquad.HaksikMatnam.matching.mapper;

import BugiSquad.HaksikMatnam.matching.entity.NoteRoom;
import BugiSquad.HaksikMatnam.matching.entity.Note;
import BugiSquad.HaksikMatnam.member.entity.Member;


public class NoteMapper {

    public static Note toEntity(NoteRoom noteRoom, Member sender, String message, boolean firstMessage) {
        return Note.builder()
                .noteRoom(noteRoom)
                .sender(sender)
                .message(message)
                .firstMessage(firstMessage)
                .build();
    }
}
