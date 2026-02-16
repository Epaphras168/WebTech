package auca.com.bonus_api.controller;



import auca.com.bonus_api.model.UserProfile;
import auca.com.bonus_api.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {


    private List<UserProfile> userList = new ArrayList<>();

    // sample data
    public UserProfileController() {
        userList.add(new UserProfile(1L, "john_doe", "john@example.com", "John Doe",
                28, "USA", "Software developer", true));
        userList.add(new UserProfile(2L, "jane_smith", "jane@example.com", "Jane Smith",
                32, "Canada", "Data scientist", true));
        userList.add(new UserProfile(3L, "bob_jones", "bob@example.com", "Bob Jones",
                22, "UK", "Student", false));
        userList.add(new UserProfile(4L, "alice_w", "alice@example.com", "Alice Wonder",
                25, "USA", "Designer", true));
        userList.add(new UserProfile(5L, "charlie_b", "charlie@example.com", "Charlie Brown",
                30, "USA", "Musician", true));
    }

    // Helper method to create ApiResponse
    private <T> ApiResponse<T> createResponse(boolean success, String message, T data) {
        return new ApiResponse<>(success, message, data);
    }

    // ------------------- BASIC CRUD -------------------

    // GET /api/users - get all users
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserProfile>>> getAllUsers() {
        ApiResponse<List<UserProfile>> response = createResponse(true, "Users retrieved successfully", userList);
        return ResponseEntity.ok(response);
    }

    // GET /api/users/{userId} - get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> getUserById(@PathVariable Long userId) {
        UserProfile user = userList.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
        if (user != null) {
            return ResponseEntity.ok(createResponse(true, "User found", user));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createResponse(false, "User not found with id: " + userId, null));
        }
    }

    // POST /api/users - create new user
    @PostMapping
    public ResponseEntity<ApiResponse<UserProfile>> createUser(@RequestBody UserProfile newUser) {
        // Generate a new ID (simple: max id + 1)
        Long newId = userList.stream().mapToLong(UserProfile::getUserId).max().orElse(0) + 1;
        newUser.setUserId(newId);
        // Ensure new user is active by default
        userList.add(newUser);
        ApiResponse<UserProfile> response = createResponse(true, "User profile created successfully", newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT /api/users/{userId} - update entire user
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> updateUser(@PathVariable Long userId,
                                                               @RequestBody UserProfile updatedUser) {
        for (int i = 0; i < userList.size(); i++) {
            UserProfile u = userList.get(i);
            if (u.getUserId().equals(userId)) {
                // Set the ID to the path variable to avoid mismatch
                updatedUser.setUserId(userId);
                userList.set(i, updatedUser);
                return ResponseEntity.ok(createResponse(true, "User updated successfully", updatedUser));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createResponse(false, "User not found with id: " + userId, null));
    }

    // DELETE /api/users/{userId}
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        boolean removed = userList.removeIf(u -> u.getUserId().equals(userId));
        if (removed) {
            return ResponseEntity.ok(createResponse(true, "User deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createResponse(false, "User not found with id: " + userId, null));
        }
    }

    // Searching
    // GET /api/users/search?username=...&country=...&minAge=...&maxAge=...
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserProfile>>> searchUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge) {

        List<UserProfile> results = userList;

        if (username != null && !username.isEmpty()) {
            results = results.stream()
                    .filter(u -> u.getUsername().toLowerCase().contains(username.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (country != null && !country.isEmpty()) {
            results = results.stream()
                    .filter(u -> u.getCountry().equalsIgnoreCase(country))
                    .collect(Collectors.toList());
        }
        if (minAge != null) {
            results = results.stream()
                    .filter(u -> u.getAge() >= minAge)
                    .collect(Collectors.toList());
        }
        if (maxAge != null) {
            results = results.stream()
                    .filter(u -> u.getAge() <= maxAge)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(createResponse(true, "Search completed", results));
    }

    // ACTIVATE/DEACTIVATE
    // PATCH /api/users/{userId}/status?active=true/false
    @PatchMapping("/{userId}/status")
    public ResponseEntity<ApiResponse<UserProfile>> setUserActive(@PathVariable Long userId,
                                                                  @RequestParam boolean active) {
        for (UserProfile u : userList) {
            if (u.getUserId().equals(userId)) {
                u.setActive(active);
                String msg = active ? "User activated" : "User deactivated";
                return ResponseEntity.ok(createResponse(true, msg, u));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createResponse(false, "User not found with id: " + userId, null));
    }
}