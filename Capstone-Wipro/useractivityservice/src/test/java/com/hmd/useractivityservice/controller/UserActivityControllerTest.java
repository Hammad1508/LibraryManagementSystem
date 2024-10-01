package com.hmd.useractivityservice.controller;

import com.hmd.useractivityservice.model.UserActivity;
import com.hmd.useractivityservice.service.UserActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserActivityControllerTest {

    @InjectMocks
    private UserActivityController userActivityController;

    @Mock
    private UserActivityService userActivityService;

    private UserActivity sampleActivity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleActivity = new UserActivity(1L, "Borrowed a book", LocalDateTime.now());
        sampleActivity.setId(1L);
    }

    @Test
    public void testLogActivity() {
        when(userActivityService.logUserActivity(any(UserActivity.class))).thenReturn(sampleActivity);

        ResponseEntity<UserActivity> response = userActivityController.logActivity(sampleActivity);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleActivity.getActivity(), response.getBody().getActivity());

        verify(userActivityService, times(1)).logUserActivity(any(UserActivity.class));
    }

    @Test
    public void testGetUserActivities() {
        when(userActivityService.getUserActivities(1L)).thenReturn(Collections.singletonList(sampleActivity));

        ResponseEntity<List<UserActivity>> response = userActivityController.getUserActivities(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());

        verify(userActivityService, times(1)).getUserActivities(1L);
    }
    
    @Test
    public void testGetUserActivitiesReturnsEmptyListInController() {
        // Given
        when(userActivityService.getUserActivities(1L)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<UserActivity>> response = userActivityController.getUserActivities(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(userActivityService, times(1)).getUserActivities(1L);
    }


    @Test
    public void testLogActivityThrowsErrorWhenNull() {
        // Given
        UserActivity nullActivity = null;

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            userActivityController.logActivity(nullActivity);
        });
    }

    @Test
    public void testLogActivitySuccessInController() {
        // Given
        when(userActivityService.logUserActivity(any(UserActivity.class))).thenReturn(sampleActivity);

        // When
        ResponseEntity<UserActivity> response = userActivityController.logActivity(sampleActivity);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleActivity.getActivity(), response.getBody().getActivity());

        verify(userActivityService, times(1)).logUserActivity(any(UserActivity.class));
    }

    @Test
    public void testGetActivityByIdSuccessInController() {
        // Given
        when(userActivityService.getActivityById(1L)).thenReturn(sampleActivity);

        // When
        ResponseEntity<UserActivity> response = userActivityController.getActivityById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleActivity.getActivity(), response.getBody().getActivity());

        verify(userActivityService, times(1)).getActivityById(1L);
    }

}
