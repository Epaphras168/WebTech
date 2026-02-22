package auca.com.question3_restaurant_api.repository;

import auca.com.question3_restaurant_api.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
