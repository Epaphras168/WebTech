# Restaurant Menu API

### Base URL: /api/menu

- GET /api/menu – Get all menu items
- GET /api/menu/{id} – Get specific menu item
- GET /api/menu/category/{category} – Get items by category
- GET /api/menu/available?available=true – Get available items
- GET /api/menu/search?name={name} – Search menu items by name
- POST /api/menu – Add new menu item
- PUT /api/menu/{id}/availability – Toggle item availability
- DELETE /api/menu/{id} – Remove menu item