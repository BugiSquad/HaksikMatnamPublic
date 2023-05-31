package BugiSquad.HaksikMatnam.matching.repository.memberNoteRoomRelation;

import BugiSquad.HaksikMatnam.matching.entity.MemberNoteRoomRelation;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemberNoteRoomRelationRepository extends JpaRepository<MemberNoteRoomRelation, Long>{


    Optional<List<MemberNoteRoomRelation>> findByMemberId(Long memberId);

    Optional<List<MemberNoteRoomRelation>> findByNoteRoomId(Long noteRoomId);

    Optional<MemberNoteRoomRelation> findByNoteRoomIdAndMemberId(Long noteRoomId, Long memberId);

    void deleteByMemberIdAndNoteRoomId(Long memberId, Long notRoomId);


}
