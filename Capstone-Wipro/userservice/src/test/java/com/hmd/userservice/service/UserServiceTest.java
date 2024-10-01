package com.hmd.userservice.service;

import com.hmd.userservice.model.User;
import com.hmd.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User(1L, "username", "email@example.com", "password");
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User foundUser = userService.getUserById(1L);
        assertNotNull(foundUser);
        assertEquals("username", foundUser.getUsername());
    }

    @Test
    void testGetUserByUsername() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        User foundUser = userService.getUserByUsername("username");
        assertNotNull(foundUser);
        assertEquals("email@example.com", foundUser.getEmail());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    // New Test Cases
    

    


    @Test
    void testLoadUserByUsername() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        UserDetails userDetails = userService.loadUserByUsername("username");
        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
    }

   

  
}
