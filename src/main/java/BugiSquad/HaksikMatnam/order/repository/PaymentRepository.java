package BugiSquad.HaksikMatnam.order.repository;

import BugiSquad.HaksikMatnam.order.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
