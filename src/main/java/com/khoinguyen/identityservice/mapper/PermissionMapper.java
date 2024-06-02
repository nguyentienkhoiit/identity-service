package com.khoinguyen.identityservice.mapper;

import com.khoinguyen.identityservice.dto.request.PermissionRequest;
import com.khoinguyen.identityservice.dto.response.PermissionResponse;
import com.khoinguyen.identityservice.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);
}
