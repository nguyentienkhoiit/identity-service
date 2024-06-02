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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping
    public ApiResponse<UserResponse> createUser(
            @RequestBody @Valid UserCreationRequest user
    ) {
        return new ApiResponse<>(userService.createUser(user));
    }

    @PreAuthorize("hasAuthority('GET_ALL_USER')")
    @GetMapping
    public ApiResponse<List<User>> getUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return new ApiResponse<>(userService.getUsers());
    }

    @PreAuthorize("hasAuthority('GET_USER_BY_ID')")
    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUser(@PathVariable String userId) {
        return new ApiResponse<>(userService.getUser(userId));
    }

    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable String userId,
            @RequestBody UserUpdateRequest request
    ) {
        return new ApiResponse<>(userService.updateUser(userId, request));
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping("/{userId}")
    public ApiResponse<UserResponse> deleteUser(@PathVariable String userId) {
        return new ApiResponse<>(userService.deleteUser(userId));
    }

    @PreAuthorize("hasAuthority('GET_MY_INFO')")
    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        return new ApiResponse<>(userService.getMyInfo());
    }
}
