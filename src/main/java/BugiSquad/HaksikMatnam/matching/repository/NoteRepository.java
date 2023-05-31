package BugiSquad.HaksikMatnam.matching.repository;

import BugiSquad.HaksikMatnam.matching.entity.Note;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<List<Note>> findByNoteRoomId(Long noteRoomId, Sort sort);

    void deleteByNoteRoomId(Long noteRoomId);

}
