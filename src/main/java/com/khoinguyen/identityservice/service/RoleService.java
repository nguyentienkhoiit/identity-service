package com.khoinguyen.identityservice.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.khoinguyen.identityservice.dto.request.RoleRequest;
import com.khoinguyen.identityservice.dto.response.RoleResponse;
import com.khoinguyen.identityservice.entity.Role;
import com.khoinguyen.identityservice.mapper.RoleMapper;
import com.khoinguyen.identityservice.repository.PermissionRepository;
import com.khoinguyen.identityservice.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        Role role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String roleName) {
        roleRepository.deleteById(roleName);
    }
}
