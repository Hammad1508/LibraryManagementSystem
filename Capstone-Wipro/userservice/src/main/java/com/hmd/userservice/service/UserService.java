package com.hmd.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.hmd.userservice.client.BookServiceClient;
import com.hmd.userservice.client.UserActivityClient;
import com.hmd.userservice.dto.BookDTO;
import com.hmd.userservice.dto.BorrowDetails;
import com.hmd.userservice.dto.UserActivityDTO;
import com.hmd.userservice.model.User;
import com.hmd.userservice.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private BookServiceClient bookServiceClient;

    @Autowired
    private UserActivityClient userActivityClient;

    // User-related methods
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public User updateUser(Long userId, User userDetails) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        if (userDetails.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
    }

    // Feign client-related methods
    public void borrowBook(Long userId, Long bookId) {
        // Call Book Service to get book details
        BookDTO book = bookServiceClient.getBookById(bookId);

        // Call Book Service to borrow the book
        BorrowDetails borrowDetails = new BorrowDetails(userId, LocalDate.now());
        bookServiceClient.borrowBook(bookId, borrowDetails);

        // Log the activity using User Activity Service
        UserActivityDTO activity = new UserActivityDTO(userId, "Borrowed book: " + book.getTitle(), LocalDateTime.now());
        userActivityClient.logActivity(activity);
    }

    public void logActivity(Long userId, String activityDescription) {
        // Log other user activities (e.g., returning a book) using User Activity Service
        UserActivityDTO userActivityDTO = new UserActivityDTO(userId, activityDescription, LocalDateTime.now());
        userActivityClient.logActivity(userActivityDTO);
    }
    
    public void returnBook(Long userId, Long bookId) {
        // Call the Book Service to mark the book as returned
        bookServiceClient.returnBook(bookId);

        // Log the returning activity
        UserActivityDTO activity = new UserActivityDTO(userId, "Returned book: " + bookId, java.time.LocalDateTime.now());
        userActivityClient.logActivity(activity);
    }
    
 // Method to fetch user activities
    public List<UserActivityDTO> getUserActivities(Long userId) {
        return userActivityClient.getUserActivities(userId);
    }
}
