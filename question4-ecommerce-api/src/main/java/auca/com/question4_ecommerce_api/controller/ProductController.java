package auca.com.question4_ecommerce_api.controller;



import auca.com.question4_ecommerce_api.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private List<Product> productList = new ArrayList<>();

    public ProductController() {
        // Create 10 sample products
        productList.add(new Product(1L, "Laptop", "High performance laptop", 1200.00, "Electronics", 10, "Dell"));
        productList.add(new Product(2L, "Smartphone", "Latest model smartphone", 800.00, "Electronics", 25, "Samsung"));
        productList.add(new Product(3L, "Headphones", "Noise-cancelling", 150.00, "Electronics", 50, "Sony"));
        productList.add(new Product(4L, "T-Shirt", "Cotton t-shirt", 20.00, "Clothing", 100, "Nike"));
        productList.add(new Product(5L, "Jeans", "Slim fit jeans", 60.00, "Clothing", 75, "Levi's"));
        productList.add(new Product(6L, "Coffee Maker", "Automatic drip", 90.00, "Home Appliances", 15, "Hamilton Beach"));
        productList.add(new Product(7L, "Blender", "High-speed blender", 130.00, "Home Appliances", 8, "NutriBullet"));
        productList.add(new Product(8L, "Book: Java Programming", "Learn Java", 45.00, "Books", 30, "O'Reilly"));
        productList.add(new Product(9L, "Notebook", "Spiral notebook", 3.00, "Stationery", 200, "Mead"));
        productList.add(new Product(10L, "Desk Lamp", "LED desk lamp", 25.00, "Furniture", 20, "IKEA"));
    }

    // GET /api/products?page=0&limit=5 - pagination
    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        int start = page * limit;
        int end = Math.min(start + limit, productList.size());
        if (start >= productList.size()) {
            return new ArrayList<>();
        }
        return productList.subList(start, end);
    }

    // GET /api/products/{productId}
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productList.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/products/category/{category}
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productList.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // GET /api/products/brand/{brand}
    @GetMapping("/brand/{brand}")
    public List<Product> getProductsByBrand(@PathVariable String brand) {
        return productList.stream()
                .filter(p -> p.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

    // GET /api/products/search?keyword={keyword}
    @GetMapping("/search")
    public List<Product> searchByKeyword(@RequestParam String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return productList.stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerKeyword) ||
                        p.getDescription().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    // GET /api/products/price-range?min={min}&max={max}
    @GetMapping("/price-range")
    public List<Product> getProductsByPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        return productList.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }

    // GET /api/products/in-stock
    @GetMapping("/in-stock")
    public List<Product> getInStockProducts() {
        return productList.stream()
                .filter(p -> p.getStockQuantity() > 0)
                .collect(Collectors.toList());
    }

    // POST /api/products
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product newProduct) {
        Long newId = productList.stream().mapToLong(Product::getProductId).max().orElse(0) + 1;
        newProduct.setProductId(newId);
        productList.add(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    // PUT /api/products/{productId}
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
        for (int i = 0; i < productList.size(); i++) {
            Product p = productList.get(i);
            if (p.getProductId().equals(productId)) {
                updatedProduct.setProductId(productId);
                productList.set(i, updatedProduct);
                return ResponseEntity.ok(updatedProduct);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH /api/products/{productId}/stock?quantity={quantity}
    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long productId, @RequestParam int quantity) {
        for (Product p : productList) {
            if (p.getProductId().equals(productId)) {
                p.setStockQuantity(quantity);
                return ResponseEntity.ok(p);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/products/{productId}
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean removed = productList.removeIf(p -> p.getProductId().equals(productId));
        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
