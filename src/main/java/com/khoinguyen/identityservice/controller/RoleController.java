package com.khoinguyen.identityservice.controller;

import com.khoinguyen.identityservice.dto.request.RoleRequest;
import com.khoinguyen.identityservice.dto.response.ApiResponse;
import com.khoinguyen.identityservice.dto.response.RoleResponse;
import com.khoinguyen.identityservice.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    ApiResponse<RoleResponse> createRole(
            @RequestBody RoleRequest request
    ) {
        return new ApiResponse<>(roleService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_ALL_ROLE')")
    ApiResponse<List<RoleResponse>> getRoles() {
        return new ApiResponse<>(roleService.getAll());
    }

    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    ApiResponse<Void> deleteRole(@PathVariable String roleId) {
        roleService.delete(roleId);
        return new ApiResponse<>(null);
    }
}
