package com.khoinguyen.identityservice.exception;

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
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception"),
    INVALID_KEY(1001, "Invalid message key"),
    USERNAME_INVALID(1002, "Username must be at least 3 characters"),
    USER_EXISTED(1003, "User existed"),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters"),
    USER_NOT_EXIST(1005, "User not exist");

    int code;
    String message;
}
