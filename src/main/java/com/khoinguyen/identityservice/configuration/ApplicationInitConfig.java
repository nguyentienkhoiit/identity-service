package com.khoinguyen.identityservice.configuration;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.khoinguyen.identityservice.entity.User;
import com.khoinguyen.identityservice.repository.RoleRepository;
import com.khoinguyen.identityservice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ApplicationInitConfig {

    @Bean
    ApplicationRunner applicationRunner(
            UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .dob(LocalDate.of(2000, 1, 1))
                        .firstname("Admin")
                        .lastname("Admin")
                        .roles(Set.of(roleRepository.findById("ADMIN").orElseThrow()))
                        .build();

                userRepository.save(user);
                log.warn("Admin user created with password is admin");
            }
        };
    }
}
