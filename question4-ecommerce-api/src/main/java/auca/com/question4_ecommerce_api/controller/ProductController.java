package auca.com.question4_ecommerce_api.controller;

import auca.com.question4_ecommerce_api.model.Product;
import auca.com.question4_ecommerce_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // GET /api/products?page=0&limit=5 
    @GetMapping
    public Page<Product> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return productService.getAllProducts(page, limit);
    }

    // GET /api/products/{productId}
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/products/category/{category}
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }

    // GET /api/products/brand/{brand}
    @GetMapping("/brand/{brand}")
    public List<Product> getProductsByBrand(@PathVariable String brand) {
        return productService.getProductsByBrand(brand);
    }

    // GET /api/products/search?keyword={keyword}
    @GetMapping("/search")
    public List<Product> searchByKeyword(@RequestParam String keyword) {
        return productService.searchByKeyword(keyword);
    }

    // GET /api/products/price-range?min={min}&max={max}
    @GetMapping("/price-range")
    public List<Product> getProductsByPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        return productService.getProductsByPriceRange(min, max);
    }

    // GET /api/products/in-stock
    @GetMapping("/in-stock")
    public List<Product> getInStockProducts() {
        return productService.getInStockProducts();
    }

    // POST /api/products
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product newProduct) {
        Product savedProduct = productService.addProduct(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    // PUT /api/products/{productId}
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
        return productService.updateProduct(productId, updatedProduct)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH /api/products/{productId}/stock?quantity={quantity}
    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long productId, @RequestParam int quantity) {
        return productService.updateStock(productId, quantity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/products/{productId}
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        if (productService.deleteProduct(productId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

