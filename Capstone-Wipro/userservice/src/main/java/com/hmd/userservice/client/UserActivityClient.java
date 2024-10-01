package com.hmd.userservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hmd.userservice.dto.UserActivityDTO;

@FeignClient(name = "useractivityservice", url = "http://localhost:8084/api/user-activities")
public interface UserActivityClient {

    @PostMapping
    void logActivity(@RequestBody UserActivityDTO userActivityDTO);
    
 // New method to fetch user activities
    @GetMapping("/{userId}")
    List<UserActivityDTO> getUserActivities(@PathVariable Long userId);
}
