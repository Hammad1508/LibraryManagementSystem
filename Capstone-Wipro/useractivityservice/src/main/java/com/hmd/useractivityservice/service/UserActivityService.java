package com.hmd.useractivityservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmd.useractivityservice.model.UserActivity;
import com.hmd.useractivityservice.repository.UserActivityRepository;

import java.util.List;

@Service
public class UserActivityService {

    private static final Logger logger = LoggerFactory.getLogger(UserActivityService.class);

    @Autowired
    private UserActivityRepository userActivityRepository;

    public UserActivity logUserActivity(UserActivity userActivity) {
        logger.info("Logging user activity for user ID: {}", userActivity.getUserId());
        UserActivity savedActivity = userActivityRepository.save(userActivity);
        logger.info("User activity logged with activity ID: {}", savedActivity.getId());
        return savedActivity;
    }

    public List<UserActivity> getUserActivities(Long userId) {
        logger.info("Fetching activities for user ID: {}", userId);
        List<UserActivity> activities = userActivityRepository.findByUserId(userId);
        logger.info("Found {} activities for user ID: {}", activities.size(), userId);
        return activities;
    }

    public UserActivity getActivityById(Long activityId) {
        logger.info("Fetching activity with ID: {}", activityId);
        UserActivity activity = userActivityRepository.findById(activityId)
                .orElseThrow(() -> {
                    logger.error("Activity not found with ID: {}", activityId);
                    return new RuntimeException("Activity not found");
                });
        logger.info("Found activity with ID: {}", activityId);
        return activity;
    }
}
