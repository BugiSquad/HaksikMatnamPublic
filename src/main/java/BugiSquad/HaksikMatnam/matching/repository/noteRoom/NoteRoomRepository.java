package BugiSquad.HaksikMatnam.matching.repository.noteRoom;

import BugiSquad.HaksikMatnam.matching.entity.NoteRoom;
import BugiSquad.HaksikMatnam.matching.repository.noteRoom.querydsl.NoteRoomRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRoomRepository extends JpaRepository<NoteRoom, Long>, NoteRoomRepositoryCustom {

    Optional<List<NoteRoom>> findByPostId(Long postId);

    void deleteByPostId(Long id);
}
