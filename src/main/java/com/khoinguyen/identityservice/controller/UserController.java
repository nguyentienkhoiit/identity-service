package com.khoinguyen.identityservice.controller;

import com.khoinguyen.identityservice.dto.request.UserCreationRequest;
import com.khoinguyen.identityservice.dto.request.UserUpdateRequest;
import com.khoinguyen.identityservice.dto.response.ApiResponse;
import com.khoinguyen.identityservice.dto.response.UserResponse;
import com.khoinguyen.identityservice.entity.User;
import com.khoinguyen.identityservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(
            @RequestBody @Valid UserCreationRequest user
    ) {
        return new ApiResponse<>(userService.createUser(user));
    }

    @GetMapping
    public ApiResponse<List<User>> getUsers() {
        return new ApiResponse<>(userService.getUsers());
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUser(@PathVariable String userId) {
        return new ApiResponse<>(userService.getUser(userId));
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable String userId,
            @RequestBody UserUpdateRequest request
    ) {
        return new ApiResponse<>(userService.updateUser(userId, request));
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<UserResponse> deleteUser(@PathVariable String userId) {
        return new ApiResponse<>(userService.deleteUser(userId));
    }
}
