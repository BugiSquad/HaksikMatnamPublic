package BugiSquad.HaksikMatnam.order.repository;

import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.order.entity.Orders;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @EntityGraph(attributePaths = {"payment", "member"})
    Optional<Orders> findById(Long id);

    @EntityGraph(attributePaths = {"payment", "member"})
    List<Orders> findAllByMember(Member member);


    @Query("SELECT o FROM Orders o WHERE o.orderType <> 'COMPLETE'")
    List<Orders> findWaitingOrdersNotComplete();


    @Query("SELECT o FROM Orders o WHERE o.orderType = 'COMPLETE' ORDER BY o.createdAt DESC")
    List<Orders> findWaitingOrdersComplete();



}
