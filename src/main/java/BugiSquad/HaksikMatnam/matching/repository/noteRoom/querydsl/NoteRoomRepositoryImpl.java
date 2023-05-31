package BugiSquad.HaksikMatnam.matching.repository.noteRoom.querydsl;

import BugiSquad.HaksikMatnam.matching.dto.GetNoteRoomDto;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static BugiSquad.HaksikMatnam.matching.entity.QMemberNoteRoomRelation.memberNoteRoomRelation;
import static BugiSquad.HaksikMatnam.matching.entity.QNoteRoom.noteRoom;
import static BugiSquad.HaksikMatnam.matching.entity.QPost.post;

public class NoteRoomRepositoryImpl implements NoteRoomRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public NoteRoomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


}
