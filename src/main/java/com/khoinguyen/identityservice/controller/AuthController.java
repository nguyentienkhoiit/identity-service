package com.khoinguyen.identityservice.controller;

import com.khoinguyen.identityservice.dto.request.AuthenticationRequest;
import com.khoinguyen.identityservice.dto.response.ApiResponse;
import com.khoinguyen.identityservice.dto.response.AuthenticationResponse;
import com.khoinguyen.identityservice.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthenticationService authenticationService;

    @PostMapping
    public ApiResponse<AuthenticationResponse> authenticate(
            @RequestBody final AuthenticationRequest request
    ) {
        return new ApiResponse<>(authenticationService.authenticate(request));
    }
}
