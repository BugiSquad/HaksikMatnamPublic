package BugiSquad.HaksikMatnam.matching.repository.memberNoteRoomRelation.queydslRepository;


import BugiSquad.HaksikMatnam.matching.entity.MemberNoteRoomRelation;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static BugiSquad.HaksikMatnam.matching.entity.QMemberNoteRoomRelation.memberNoteRoomRelation;
import static BugiSquad.HaksikMatnam.matching.entity.QNoteRoom.noteRoom;


public class MemberNoteRoomRelationDetailRepositoryImpl implements MemberNoteRoomRelationDetailRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public MemberNoteRoomRelationDetailRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }



    @Override
    public Optional<List<MemberNoteRoomRelation>> findByPostId(Long memberId,Long postId) {
        QueryResults<MemberNoteRoomRelation> results = queryFactory.select(memberNoteRoomRelation)
                .from(memberNoteRoomRelation)
                .leftJoin(memberNoteRoomRelation.noteRoom, noteRoom).fetchJoin()
                .where(memberNoteRoomRelation.member.id.eq(memberId).and(noteRoom.post.id.eq(postId)))
                .fetchResults();

        return Optional.ofNullable(results.getResults());
    }
}