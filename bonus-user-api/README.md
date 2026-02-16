# User Profile API 

A simple Spring Boot REST API for managing user profiles, demonstrating CRUD operations, search, and activation/deactivation, with all responses wrapped in a custom ApiResponse object.

## Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{userId}` | Get user by ID |
| POST | `/api/users` | Create a new user |
| PUT | `/api/users/{userId}` | Update an existing user |
| DELETE | `/api/users/{userId}` | Delete a user |
| GET | `/api/users/search?username=&country=&minAge=&maxAge=` | Search users by optional filters |
| PATCH | `/api/users/{userId}/status?active=true/false` | Activate or deactivate a user |

## Sample Requests

### GET all users
GET http://localhost:8080/api/users

### POST create user
POST http://localhost:8080/api/users
Content-Type: application/json

{
"username": "john_doe",
"email": "john@example.com",
"fullName": "John Doe",
"age": 28,
"country": "USA",
"bio": "Developer",
"active": true
}

text

### Response format
All responses follow the structure:
```json
{
    "success": true/false,
    "message": "Some message",
    "data": { ... } or [ ... ] or null
}
