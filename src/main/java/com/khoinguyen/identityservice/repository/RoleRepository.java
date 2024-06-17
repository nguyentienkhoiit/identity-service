package com.khoinguyen.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khoinguyen.identityservice.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {}
