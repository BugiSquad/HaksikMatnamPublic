package BugiSquad.HaksikMatnam.member.repository;

import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.entity.Review;
import BugiSquad.HaksikMatnam.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"member", "menu"})
    Optional<Review> findById(Long id);

    @EntityGraph(attributePaths = {"member", "menu"})
    List<Review> findAllByMember(Member member);

    @EntityGraph(attributePaths = {"member", "menu"})
    List<Review> findAllByMenu(Menu menu);
}
