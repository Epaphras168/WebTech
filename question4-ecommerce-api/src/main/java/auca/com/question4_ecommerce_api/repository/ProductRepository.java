package auca.com.question4_ecommerce_api.repository;

import auca.com.question4_ecommerce_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByBrand(String brand);
    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
    List<Product> findByPriceBetween(Double min, Double max);
    List<Product> findByStockQuantityGreaterThan(int stockQuantity);
}
