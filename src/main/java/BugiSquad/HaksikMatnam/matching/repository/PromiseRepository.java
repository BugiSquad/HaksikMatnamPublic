package BugiSquad.HaksikMatnam.matching.repository;

import BugiSquad.HaksikMatnam.matching.entity.Promise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PromiseRepository extends JpaRepository<Promise, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Promise p SET p.noteRoom = NULL WHERE p.noteRoom.id = :noteRoomId")
    void updateNoteRoomIdToNull(@Param(value = "noteRoomId") Long noteRoomId);
}
