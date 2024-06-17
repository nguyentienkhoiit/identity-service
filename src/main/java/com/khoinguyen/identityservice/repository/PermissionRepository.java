package com.khoinguyen.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khoinguyen.identityservice.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {}
