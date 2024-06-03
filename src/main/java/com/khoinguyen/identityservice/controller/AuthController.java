package com.khoinguyen.identityservice.controller;

import com.khoinguyen.identityservice.dto.request.AuthenticationRequest;
import com.khoinguyen.identityservice.dto.request.IntrospectRequest;
import com.khoinguyen.identityservice.dto.request.LogoutRequest;
import com.khoinguyen.identityservice.dto.request.RefreshRequest;
import com.khoinguyen.identityservice.dto.response.ApiResponse;
import com.khoinguyen.identityservice.dto.response.AuthenticationResponse;
import com.khoinguyen.identityservice.dto.response.IntrospectResponse;
import com.khoinguyen.identityservice.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthenticationService authenticationService;

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authenticate(
            @RequestBody IntrospectRequest request
    ) throws JOSEException, ParseException {
        return new ApiResponse<>(authenticationService.introspect(request));
    }

    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return new ApiResponse<>(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> authenticate(
            @RequestBody RefreshRequest request
    ) throws ParseException, JOSEException {
        return new ApiResponse<>(authenticationService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(
            @RequestBody LogoutRequest request
    ) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }
}
