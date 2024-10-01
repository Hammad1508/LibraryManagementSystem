package com.hmd.library.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hmd.library.dto.UserDTO;

@FeignClient(name = "userservice" , url = "http://localhost:8082/api/users")
public interface UserServiceClient {
    @GetMapping("/{userId}")
    UserDTO getUserById(@PathVariable Long userId);
}

