package BugiSquad.HaksikMatnam.information.repository;

import BugiSquad.HaksikMatnam.information.entity.Information;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationRepository extends JpaRepository<Information, Long> {
}
