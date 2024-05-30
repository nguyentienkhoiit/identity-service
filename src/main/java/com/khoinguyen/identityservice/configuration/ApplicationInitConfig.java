package com.khoinguyen.identityservice.configuration;

import com.khoinguyen.identityservice.entity.User;
import com.khoinguyen.identityservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Set;

import static com.khoinguyen.identityservice.enums.Role.ADMIN;

@Slf4j
@Configuration
public class ApplicationInitConfig {

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .dob(LocalDate.of(2000, 1, 1))
                        .firstname("Admin")
                        .lastname("Admin")
                        .roles(Set.of(ADMIN.name()))
                        .build();

                userRepository.save(user);
                log.warn("Admin user created with password is admin");
            }
        };
    }
}
