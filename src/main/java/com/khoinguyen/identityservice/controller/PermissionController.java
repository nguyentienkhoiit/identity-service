package com.khoinguyen.identityservice.controller;

import com.khoinguyen.identityservice.dto.request.PermissionRequest;
import com.khoinguyen.identityservice.dto.response.ApiResponse;
import com.khoinguyen.identityservice.dto.response.PermissionResponse;
import com.khoinguyen.identityservice.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_PERMISSION')")
    ApiResponse<PermissionResponse> createPermission(
            @RequestBody PermissionRequest request
    ) {
        return new ApiResponse<>(permissionService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_ALL_PERMISSION')")
    ApiResponse<List<PermissionResponse>> getPermissions() {
        return new ApiResponse<>(permissionService.getAll());
    }

    @DeleteMapping("/{permissionId}")
    ApiResponse<Void> deletePermission(@PathVariable String permissionId) {
        permissionService.delete(permissionId);
        return new ApiResponse<>(null);
    }
}
