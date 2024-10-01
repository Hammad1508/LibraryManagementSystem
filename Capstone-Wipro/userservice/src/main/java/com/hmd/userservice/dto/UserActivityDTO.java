package com.hmd.userservice.dto;

import java.time.LocalDateTime;

public class UserActivityDTO {
    private Long userId;
    private String activity;
    private LocalDateTime activityTime;

    // Constructors
    public UserActivityDTO() {}

    public UserActivityDTO(Long userId, String activity, LocalDateTime activityTime) {
        this.userId = userId;
        this.activity = activity;
        this.activityTime = activityTime;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public LocalDateTime getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(LocalDateTime activityTime) {
        this.activityTime = activityTime;
    }
}
