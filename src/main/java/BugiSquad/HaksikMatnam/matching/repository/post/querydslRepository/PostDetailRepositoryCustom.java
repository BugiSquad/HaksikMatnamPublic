package BugiSquad.HaksikMatnam.matching.repository.post.querydslRepository;

import BugiSquad.HaksikMatnam.matching.dto.GetPostDto;
import BugiSquad.HaksikMatnam.matching.etc.PostCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostDetailRepositoryCustom {
    Page<GetPostDto> findAllGetPostDtos(PostCondition postCondition, Pageable pageable) throws NoSuchFieldException, IllegalAccessException;


}
