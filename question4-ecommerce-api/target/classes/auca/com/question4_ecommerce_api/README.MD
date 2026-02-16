# E-Commerce Product API

### Base URL: /api/products

- GET /api/products?page={page}&limit={limit} – Get all products (optional pagination)
- GET /api/products/{productId} – Get product details
- GET /api/products/category/{category} – Get products by category
- GET /api/products/brand/{brand} – Get products by brand
- GET /api/products/search?keyword={keyword} – Search by keyword
- GET /api/products/price-range?min={min}&max={max} – Filter by price range
- GET /api/products/in-stock – Get products in stock
- POST /api/products – Add new product
