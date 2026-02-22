package auca.com.question3_restaurant_api.controller;

import auca.com.question3_restaurant_api.model.MenuItem;
import auca.com.question3_restaurant_api.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    // GET /api/menu 
    @GetMapping
    public List<MenuItem> getAllItems() {
        return menuService.getAllMenuItems();
    }

    // GET /api/menu/{id} 
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getItemById(@PathVariable Long id) {
        Optional<MenuItem> item = menuService.getMenuItemById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/menu 
    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem newItem) {
        MenuItem savedItem = menuService.createMenuItem(newItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

    // PUT /api/menu/{id} 
    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem updatedItem) {
        if (menuService.getMenuItemById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        MenuItem savedItem = menuService.updateMenuItem(id, updatedItem);
        return ResponseEntity.ok(savedItem);
    }


    // DELETE /api/menu/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        if (menuService.getMenuItemById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        menuService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }
}

