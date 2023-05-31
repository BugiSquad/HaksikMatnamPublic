package BugiSquad.HaksikMatnam.matching.repository.post.querydslRepository;

import BugiSquad.HaksikMatnam.matching.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDetailRepository extends JpaRepository<Post, Long>, PostDetailRepositoryCustom {

}
