package com.khoinguyen.identityservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    private final String[] PUBLIC_ENDPOINTS = {
            "/users",
            "/auth/token",
            "/auth/introspect"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers(POST, PUBLIC_ENDPOINTS).permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                    oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
                );
        return httpSecurity.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }
}
