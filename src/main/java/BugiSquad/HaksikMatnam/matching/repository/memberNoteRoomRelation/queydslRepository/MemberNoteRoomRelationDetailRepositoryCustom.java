package BugiSquad.HaksikMatnam.matching.repository.memberNoteRoomRelation.queydslRepository;

import BugiSquad.HaksikMatnam.matching.entity.MemberNoteRoomRelation;

import java.util.List;
import java.util.Optional;

public interface MemberNoteRoomRelationDetailRepositoryCustom {
    Optional<List<MemberNoteRoomRelation>> findByPostId(Long memberId, Long id);
}
