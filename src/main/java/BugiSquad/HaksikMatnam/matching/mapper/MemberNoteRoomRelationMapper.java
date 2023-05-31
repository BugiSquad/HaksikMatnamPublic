package BugiSquad.HaksikMatnam.matching.mapper;

import BugiSquad.HaksikMatnam.matching.dto.MemberNoteRoomRelationDto;
import BugiSquad.HaksikMatnam.matching.entity.NoteRoom;
import BugiSquad.HaksikMatnam.matching.entity.MemberNoteRoomRelation;
import BugiSquad.HaksikMatnam.member.entity.Member;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberNoteRoomRelationMapper {

    public static MemberNoteRoomRelation toEntity(Member member, NoteRoom noteRoom) {

        MemberNoteRoomRelation memberNoteRoomRelation = MemberNoteRoomRelation
                .builder()
                .member(member)
                .noteRoom(noteRoom)
                .build();

        return memberNoteRoomRelation;
    }

    public static MemberNoteRoomRelationDto toDto(MemberNoteRoomRelation memberNoteRoomRelation) {

        return MemberNoteRoomRelationDto.builder()
                .relation_id(memberNoteRoomRelation.getId())
                .noteRoom(NoteRoomMapper.toDto(memberNoteRoomRelation.getNoteRoom()))
                .noReadCount(memberNoteRoomRelation.getNoReadCount())
                .lastMessage(memberNoteRoomRelation.getLastMessage())
                .lastModifiedAt(memberNoteRoomRelation.getModifiedAt())
                .build();
    }
}
