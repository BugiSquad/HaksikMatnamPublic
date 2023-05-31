package BugiSquad.HaksikMatnam.order.repository;

import BugiSquad.HaksikMatnam.menu.entity.Menu;
import BugiSquad.HaksikMatnam.order.entity.MenuOrdersItem;
import BugiSquad.HaksikMatnam.order.entity.Orders;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuOrderItemRepository extends JpaRepository<MenuOrdersItem, Long> {

    @EntityGraph(attributePaths = {"menu", "orders"})
    List<MenuOrdersItem> findAll();

    @EntityGraph(attributePaths = {"menu", "orders"})
    List<MenuOrdersItem> findAllByOrders(Orders orders);

    @EntityGraph(attributePaths = {"menu", "orders"})
    Optional<MenuOrdersItem> findById(Long id);

    void deleteByOrdersId(Long id);
}
