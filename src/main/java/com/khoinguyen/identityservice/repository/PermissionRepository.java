package com.khoinguyen.identityservice.repository;

import com.khoinguyen.identityservice.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {
}
