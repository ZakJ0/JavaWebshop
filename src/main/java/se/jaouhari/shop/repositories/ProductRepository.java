package se.jaouhari.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jaouhari.shop.entities.Product;


import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByNameContainingOrCategoryContainingOrColorContaining(String searchTerm, String searchTerm1, String searchTerm2);

    List<Product> findByProductId(int id);
    Optional<Product> findById(int id);


    List<Product> findByCategory(String computer);
}
