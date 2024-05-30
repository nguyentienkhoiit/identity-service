package com.khoinguyen.identityservice.service;

import com.khoinguyen.identityservice.dto.request.AuthenticationRequest;
import com.khoinguyen.identityservice.dto.request.IntrospectRequest;
import com.khoinguyen.identityservice.dto.response.AuthenticationResponse;
import com.khoinguyen.identityservice.dto.response.IntrospectResponse;
import com.khoinguyen.identityservice.entity.User;
import com.khoinguyen.identityservice.exception.AppException;
import com.khoinguyen.identityservice.exception.ErrorCode;
import com.khoinguyen.identityservice.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!matches) throw new AppException(ErrorCode.UNAUTHENTICATED);

        String token = generateToken(user.getUsername());
        return AuthenticationResponse.builder()
                .isAuthenticated(true)
                .token(token)
                .build();
    }

    private String generateToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("nguyentienkhoi.it")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("customClaim", "customClaimValue")
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot sign JWT object", e);
            throw new RuntimeException(e);
        }
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
        try {
            var token = request.getToken();
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            var verify = signedJWT.verify(verifier);
            return IntrospectResponse.builder()
                    .valid(verify && expirationTime.after(new Date()))
                    .build();
        } catch (JOSEException | ParseException e) {
            log.error("Cannot verify JWT token", e);
            throw new AppException(ErrorCode.TOKEN_VERIFICATION_FAILED);
        }
    }

}
