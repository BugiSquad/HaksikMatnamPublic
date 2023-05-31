package BugiSquad.HaksikMatnam.menu.repository;

import BugiSquad.HaksikMatnam.menu.entity.Menu;
import BugiSquad.HaksikMatnam.menu.etc.MenuCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByCategory(MenuCategory category);
}
