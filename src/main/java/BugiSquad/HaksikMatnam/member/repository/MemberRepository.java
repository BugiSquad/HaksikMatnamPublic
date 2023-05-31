package BugiSquad.HaksikMatnam.member.repository;

import BugiSquad.HaksikMatnam.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = {"interest"})
    Optional<Member> findById(Long id);

    @EntityGraph(attributePaths = {"interest"})
    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = {"interest"})
    Optional<Member> findByName(String name);
}
