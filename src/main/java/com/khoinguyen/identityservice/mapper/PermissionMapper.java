package com.khoinguyen.identityservice.mapper;

import org.mapstruct.Mapper;

import com.khoinguyen.identityservice.dto.request.PermissionRequest;
import com.khoinguyen.identityservice.dto.response.PermissionResponse;
import com.khoinguyen.identityservice.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);
}
