package com.khoinguyen.identityservice.repository;

import com.khoinguyen.identityservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
