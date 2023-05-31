package BugiSquad.HaksikMatnam.message.repository;

import BugiSquad.HaksikMatnam.message.entity.Subscriptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionsRepository extends JpaRepository<Subscriptions, Long> {

    Optional<Subscriptions> findByEndpoint(String endpoint);
    Optional<Subscriptions> findByMemberId(Long memberId);
}
