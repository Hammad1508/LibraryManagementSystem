package com.hmd.library.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hmd.library.dto.UserActivityDTO;

@FeignClient(name = "user-activity-service", url = "http://localhost:8084/api/user-activities") // Update the URL accordingly
public interface UserActivityClient {
    @PostMapping("/log")
    void logActivity(@RequestBody UserActivityDTO activityDTO);
}

