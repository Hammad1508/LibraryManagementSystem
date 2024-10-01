package com.hmd.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hmd.userservice.dto.UserActivityDTO;
import com.hmd.userservice.model.User;
import com.hmd.userservice.service.UserService;
import com.hmd.userservice.util.JwtUtil;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        // Check if the user exists by username
        User user = userService.getUserByUsername(loginRequest.getUsername());

        // If user exists and password matches, generate JWT token
        if (user != null && userService.checkPassword(user, loginRequest.getPassword())) {
            // Generate JWT token using username
            String token = jwtUtil.generateToken(user.getUsername());
            AuthenticationResponse authResponse = new AuthenticationResponse(token);
            
            // Return the token in the response
            return ResponseEntity.ok(authResponse);
        }

        // If credentials are invalid, return 401 Unauthorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }


    // Register Endpoint
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    // Get User by ID
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // Update User by ID
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(userId, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete User by ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    // Helper class for JWT response
    class AuthenticationResponse {
        private String jwt;

        public AuthenticationResponse(String jwt) {
            this.jwt = jwt;
        }

        public String getJwt() {
            return jwt;
        }
    }
    
    @PutMapping("/{userId}/borrow/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long userId, @PathVariable Long bookId) {
        userService.borrowBook(userId, bookId);
        return ResponseEntity.ok("Book borrowed successfully");
    }

    @PutMapping("/{userId}/return/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable Long userId, @PathVariable Long bookId) {
        userService.returnBook(userId, bookId);
        return ResponseEntity.ok("Book returned successfully");
    }
    
    @PostMapping("/{userId}/log-activity")
    public ResponseEntity<String> logUserActivity(@PathVariable Long userId, @RequestParam String activityDescription) {
        userService.logActivity(userId, activityDescription);
        return ResponseEntity.status(HttpStatus.OK).body("Activity logged successfully");
    }
    
 // New endpoint to get user activities
    @GetMapping("/{userId}/activities")
    public ResponseEntity<List<UserActivityDTO>> getUserActivities(@PathVariable Long userId) {
        List<UserActivityDTO> activities = userService.getUserActivities(userId);
        return ResponseEntity.ok(activities);
    }
}
