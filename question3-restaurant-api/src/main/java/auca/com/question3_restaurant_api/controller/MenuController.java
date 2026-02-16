package auca.com.question3_restaurant_api.controller;

import auca.com.question3_restaurant_api.model.MenuItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private List<MenuItem> menuItems = new ArrayList<>();

    public MenuController() {
        // Create at least 8 sample menu items across categories
        menuItems.add(new MenuItem(1L, "Garlic Bread", "Toasted bread with garlic butter", 4.99, "Appetizer", true));
        menuItems.add(new MenuItem(2L, "Bruschetta", "Tomato, basil on toasted bread", 6.99, "Appetizer", true));
        menuItems.add(new MenuItem(3L, "Grilled Salmon", "Salmon with lemon butter sauce", 15.99, "Main Course", true));
        menuItems.add(new MenuItem(4L, "Steak", "Ribeye steak with mashed potatoes", 22.99, "Main Course", true));
        menuItems.add(new MenuItem(5L, "Caesar Salad", "Romaine lettuce, croutons, parmesan", 8.99, "Main Course", true));
        menuItems.add(new MenuItem(6L, "Cheesecake", "New York style cheesecake", 5.99, "Dessert", true));
        menuItems.add(new MenuItem(7L, "Chocolate Lava Cake", "Warm chocolate cake", 6.99, "Dessert", false));
        menuItems.add(new MenuItem(8L, "Coca Cola", "Soft drink", 1.99, "Beverage", true));
    }

    // GET /api/menu - all items
    @GetMapping
    public List<MenuItem> getAllItems() {
        return menuItems;
    }

    // GET /api/menu/{id} - specific item
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getItemById(@PathVariable Long id) {
        MenuItem item = menuItems.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/menu/category/{category}
    @GetMapping("/category/{category}")
    public List<MenuItem> getItemsByCategory(@PathVariable String category) {
        return menuItems.stream()
                .filter(m -> m.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // GET /api/menu/available?available=true
    @GetMapping("/available")
    public List<MenuItem> getAvailableItems(@RequestParam boolean available) {
        return menuItems.stream()
                .filter(m -> m.isAvailable() == available)
                .collect(Collectors.toList());
    }

    // GET /api/menu/search?name={name}
    @GetMapping("/search")
    public List<MenuItem> searchByName(@RequestParam String name) {
        return menuItems.stream()
                .filter(m -> m.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // POST /api/menu - add new item
    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem newItem) {
        Long newId = menuItems.stream().mapToLong(MenuItem::getId).max().orElse(0) + 1;
        newItem.setId(newId);
        menuItems.add(newItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
    }

    // PUT /api/menu/{id}/availability - toggle availability
    @PutMapping("/{id}/availability")
    public ResponseEntity<MenuItem> toggleAvailability(@PathVariable Long id) {
        for (MenuItem item : menuItems) {
            if (item.getId().equals(id)) {
                item.setAvailable(!item.isAvailable());
                return ResponseEntity.ok(item);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/menu/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        boolean removed = menuItems.removeIf(item -> item.getId().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
