package me.chahyunho.msaorder.repository;

import me.chahyunho.msaorder.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUserId(Long userId);
}
