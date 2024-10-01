package com.hmd.useractivityservice.service;

import com.hmd.useractivityservice.model.UserActivity;
import com.hmd.useractivityservice.repository.UserActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserActivityServiceTest {

    @InjectMocks
    private UserActivityService userActivityService;

    @Mock
    private UserActivityRepository userActivityRepository;

    private UserActivity sampleActivity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a sample UserActivity object
        sampleActivity = new UserActivity(1L, "Borrowed a book", LocalDateTime.now());
        sampleActivity.setId(1L);
    }

    @Test
    public void testLogUserActivity() {
        // Given
        when(userActivityRepository.save(sampleActivity)).thenReturn(sampleActivity);

        // When
        UserActivity createdActivity = userActivityService.logUserActivity(sampleActivity);

        // Then
        assertNotNull(createdActivity);
        assertEquals(sampleActivity.getId(), createdActivity.getId());
        assertEquals(sampleActivity.getActivity(), createdActivity.getActivity());

        verify(userActivityRepository, times(1)).save(sampleActivity);
    }

    @Test
    public void testGetUserActivities() {
        // Given
        List<UserActivity> activities = new ArrayList<>();
        activities.add(sampleActivity);

        when(userActivityRepository.findByUserId(1L)).thenReturn(activities);

        // When
        List<UserActivity> result = userActivityService.getUserActivities(1L);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(sampleActivity.getUserId(), result.get(0).getUserId());

        verify(userActivityRepository, times(1)).findByUserId(1L);
    }
    
    @Test
    public void testGetUserActivitiesReturnsEmptyList() {
        // Given
        when(userActivityRepository.findByUserId(1L)).thenReturn(Collections.emptyList());

        // When
        List<UserActivity> result = userActivityService.getUserActivities(1L);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty(), "The list of activities should be empty");

        verify(userActivityRepository, times(1)).findByUserId(1L);
    }
    
    @Test
    public void testGetActivityByIdThrowsExceptionWhenNotFound() {
        // Given
        when(userActivityRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userActivityService.getActivityById(1L);
        });

        assertEquals("Activity not found", exception.getMessage());
        verify(userActivityRepository, times(1)).findById(1L);
    }

    @Test
    public void testLogUserActivityThrowsExceptionWhenNull() {
        // Given a null UserActivity
        UserActivity nullActivity = null;

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            userActivityService.logUserActivity(nullActivity);
        });
    }

    @Test
    public void testGetUserActivitiesWithNonExistentUserId() {
        // Given a non-existent userId
        when(userActivityRepository.findByUserId(99L)).thenReturn(Collections.emptyList());

        // When
        List<UserActivity> result = userActivityService.getUserActivities(99L);

        // Then
        assertTrue(result.isEmpty(), "The list should be empty for a non-existent userId");
        verify(userActivityRepository, times(1)).findByUserId(99L);
    }

    @Test
    public void testGetActivityByIdSuccess() {
        // Given
        when(userActivityRepository.findById(1L)).thenReturn(Optional.of(sampleActivity));

        // When
        UserActivity result = userActivityService.getActivityById(1L);

        // Then
        assertNotNull(result);
        assertEquals(sampleActivity.getActivity(), result.getActivity());
        verify(userActivityRepository, times(1)).findById(1L);
    }


}
