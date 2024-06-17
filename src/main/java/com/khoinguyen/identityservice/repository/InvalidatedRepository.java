package com.khoinguyen.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khoinguyen.identityservice.entity.InvalidatedToken;

public interface InvalidatedRepository extends JpaRepository<InvalidatedToken, String> {}
