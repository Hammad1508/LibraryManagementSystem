package com.hmd.useractivityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hmd.useractivityservice.model.UserActivity;
import com.hmd.useractivityservice.service.UserActivityService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/user-activities")
public class UserActivityController {

    @Autowired
    private UserActivityService userActivityService;

    @PostMapping
    public ResponseEntity<UserActivity> logActivity(@RequestBody UserActivity userActivity) {
        userActivity.setActivityTime(LocalDateTime.now()); // Automatically set activity time
        UserActivity createdActivity = userActivityService.logUserActivity(userActivity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdActivity);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserActivity>> getUserActivities(@PathVariable Long userId) {
        List<UserActivity> activities = userActivityService.getUserActivities(userId);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<UserActivity> getActivityById(@PathVariable Long activityId) {
        UserActivity activity = userActivityService.getActivityById(activityId);
        return ResponseEntity.ok(activity);
    }
}