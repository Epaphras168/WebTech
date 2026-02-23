package auca.com.bonus_api.controller;

import auca.com.bonus_api.dto.ApiResponse;
import auca.com.bonus_api.model.UserProfile;
import auca.com.bonus_api.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    // GET /api/users - get all users
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserProfile>>> getAllUsers() {
        List<UserProfile> userProfiles = userProfileService.getAllUserProfiles();
        ApiResponse<List<UserProfile>> response = new ApiResponse<>(true, "Users retrieved successfully", userProfiles);
        return ResponseEntity.ok(response);
    }

    // GET /api/users/{userId} - get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> getUserById(@PathVariable Long userId) {
        Optional<UserProfile> userProfile = userProfileService.getUserProfileById(userId);
        return userProfile.map(profile -> ResponseEntity.ok(new ApiResponse<>(true, "User found", profile)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User not found with id: " + userId, null)));
    }

    // POST /api/users - create new user
    @PostMapping
    public ResponseEntity<ApiResponse<UserProfile>> createUser(@RequestBody UserProfile newUser) {
        UserProfile savedUser = userProfileService.saveUserProfile(newUser);
        ApiResponse<UserProfile> response = new ApiResponse<>(true, "User profile created successfully", savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT /api/users/{userId} - update entire user
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> updateUser(@PathVariable Long userId,
                                                               @RequestBody UserProfile updatedUser) {
        return userProfileService.getUserProfileById(userId)
                .map(existingUser -> {
                    updatedUser.setId(userId);
                    UserProfile savedUser = userProfileService.saveUserProfile(updatedUser);
                    return ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully", savedUser));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User not found with id: " + userId, null)));
    }

    // DELETE /api/users/{userId}
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        return userProfileService.getUserProfileById(userId)
                .map(existingUser -> {
                    userProfileService.deleteUserProfile(userId);
                    return ResponseEntity.ok(new ApiResponse<Void>(true, "User deleted successfully", null));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User not found with id: " + userId, null)));
    }
}