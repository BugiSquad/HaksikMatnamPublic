package BugiSquad.HaksikMatnam.matching.repository.memberNoteRoomRelation.queydslRepository;

import BugiSquad.HaksikMatnam.matching.entity.MemberNoteRoomRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberNoteRoomRelationDetailRepository extends JpaRepository<MemberNoteRoomRelation, Long>, MemberNoteRoomRelationDetailRepositoryCustom {

}
