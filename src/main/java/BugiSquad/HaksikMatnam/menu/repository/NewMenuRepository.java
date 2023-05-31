package BugiSquad.HaksikMatnam.menu.repository;

import BugiSquad.HaksikMatnam.menu.entity.NewMenu;
import BugiSquad.HaksikMatnam.menu.etc.MenuCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewMenuRepository extends JpaRepository<NewMenu, Long> {

    List<NewMenu> findAllByCategory(MenuCategory category);
}
