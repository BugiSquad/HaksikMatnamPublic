package BugiSquad.HaksikMatnam.member.repository;

import BugiSquad.HaksikMatnam.member.entity.ShoppingCart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @EntityGraph(attributePaths = {"member"})
    Optional<ShoppingCart> findById(Long id);
}
