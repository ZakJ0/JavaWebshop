package se.jaouhari.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jaouhari.shop.entities.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findOrderByCustomerId(int id);

    List<Order> findByStatus(String pending);
}

