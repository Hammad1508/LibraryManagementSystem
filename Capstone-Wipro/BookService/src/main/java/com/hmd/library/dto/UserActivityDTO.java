package com.hmd.library.dto;

import java.time.LocalDateTime;

public class UserActivityDTO {
    private Long userId;
    private String activityType;
    private Long bookId;
    private LocalDateTime activityTime; // Optional, can be set on the backend in UserActivityService
	
    
    public UserActivityDTO() {}
    
    public UserActivityDTO(Long userId, String activityType, Long bookId, LocalDateTime activityTime) {
		
		this.userId = userId;
		this.activityType = activityType;
		this.bookId = bookId;
		this.activityTime = activityTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public LocalDateTime getActivityTime() {
		return activityTime;
	}
	public void setActivityTime(LocalDateTime activityTime) {
		this.activityTime = activityTime;
	}

    // Getters and setters
    
    
}

