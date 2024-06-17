package com.khoinguyen.identityservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION("Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY("Invalid message key", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID("Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_EXISTED("User existed", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID("Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST("User not exist", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED("Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("You do not permission", HttpStatus.FORBIDDEN),
    TOKEN_VERIFICATION_FAILED("Token verification failed", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_DOB("Your age must be at least {min}", HttpStatus.BAD_REQUEST);

    String message;
    HttpStatusCode statusCode;
}
