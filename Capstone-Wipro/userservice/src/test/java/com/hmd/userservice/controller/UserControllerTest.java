package com.hmd.userservice.controller;

import com.hmd.userservice.model.User;
import com.hmd.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User(1L, "username", "email@example.com", "password");
    }

    @Test
    void testRegisterUser() {
        when(userService.createUser(any(User.class))).thenReturn(user);
        ResponseEntity<User> response = userController.registerUser(user);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testGetUserById() {
        when(userService.getUserById(1L)).thenReturn(user);
        ResponseEntity<User> response = userController.getUserById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateUser() {
        when(userService.updateUser(1L, user)).thenReturn(user);
        ResponseEntity<User> response = userController.updateUser(1L, user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // New Test Cases
    @Test
    void testDeleteUser() {
        doNothing().when(userService).deleteUser(1L);
        ResponseEntity<String> response = userController.deleteUser(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());
    }

    

    @Test
    void testLoginUserFailure() {
        when(userService.getUserByUsername("username")).thenReturn(user);
        when(userService.checkPassword(any(User.class), eq("wrongpassword"))).thenReturn(false);
        
        ResponseEntity<?> response = userController.loginUser(new User(null, "username", null, "wrongpassword"));
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    void testBorrowBook() {
        doNothing().when(userService).borrowBook(1L, 1L);
        ResponseEntity<String> response = userController.borrowBook(1L, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book borrowed successfully", response.getBody());
    }

    @Test
    void testReturnBook() {
        doNothing().when(userService).returnBook(1L, 1L);
        ResponseEntity<String> response = userController.returnBook(1L, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book returned successfully", response.getBody());
    }

    @Test
    void testLogUserActivity() {
        doNothing().when(userService).logActivity(1L, "Test activity");
        ResponseEntity<String> response = userController.logUserActivity(1L, "Test activity");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Activity logged successfully", response.getBody());
    }

   
}
