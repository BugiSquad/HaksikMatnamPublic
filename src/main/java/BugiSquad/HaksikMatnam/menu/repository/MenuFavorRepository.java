package BugiSquad.HaksikMatnam.menu.repository;

import BugiSquad.HaksikMatnam.menu.entity.MenuFavor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuFavorRepository extends JpaRepository<MenuFavor, Long> {

    List<MenuFavor> findAllByCountsYearAndCountsMonth(int year, int month);
}
