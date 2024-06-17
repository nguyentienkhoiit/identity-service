package com.khoinguyen.identityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.khoinguyen.identityservice.dto.request.RoleRequest;
import com.khoinguyen.identityservice.dto.response.RoleResponse;
import com.khoinguyen.identityservice.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
