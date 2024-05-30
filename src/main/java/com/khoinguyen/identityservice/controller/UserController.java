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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
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
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());

        authentication.getAuthorities().forEach(g -> log.info("GrantedAuthority: {}", g.getAuthority()));

        return new ApiResponse<>(userService.getUsers());
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUser(@PathVariable String userId) {
        log.info("In method get user by id");
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

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        return new ApiResponse<>(userService.getMyInfo());
    }
}
