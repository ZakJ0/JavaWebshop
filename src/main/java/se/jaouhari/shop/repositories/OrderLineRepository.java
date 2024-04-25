package se.jaouhari.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jaouhari.shop.entities.Orderline;


public interface OrderLineRepository extends JpaRepository<Orderline, Integer> {

}
