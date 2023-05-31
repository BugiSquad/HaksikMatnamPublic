package BugiSquad.HaksikMatnam.matching.repository.post;

import BugiSquad.HaksikMatnam.matching.entity.Post;
import BugiSquad.HaksikMatnam.matching.etc.GroupType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    Optional<List<Post>> findByMemberIdAndGroupTypeNot(Long memberId, GroupType type);
    Optional<List<Post>> findByMemberId(Long memberId);

}
