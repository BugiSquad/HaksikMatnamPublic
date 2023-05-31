package BugiSquad.HaksikMatnam.matching.repository.PromiseMemberRelation;

import BugiSquad.HaksikMatnam.matching.entity.PromiseMemberRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PromiseMemberRelationRepository extends JpaRepository<PromiseMemberRelation, Long>, PromiseMemberRelationRepositoryCustom {
    Optional<List<PromiseMemberRelation>> findByMemberId(Long memberId);

    Optional<List<PromiseMemberRelation>> findByPromiseId(Long myPromiseId);

}
