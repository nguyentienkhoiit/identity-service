package com.khoinguyen.identityservice.exception;

import java.util.Map;
import java.util.Objects;

import jakarta.validation.ConstraintViolation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.khoinguyen.identityservice.dto.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";
    private static final String MAX_ATTRIBUTE = "max";

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException exception) {
        ApiResponse<?> apiResponse = new ApiResponse<>();

        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatusCode().value());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<?>> handleAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getStatusCode().value())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getStatusCode().value())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
            attributes = getConstraintViolationAttributes(exception);
        } catch (IllegalArgumentException e) {
            log.error(errorCode.getMessage());
        }

        String message =
                !Objects.isNull(attributes) ? mapAttribute(errorCode.getMessage(), attributes) : errorCode.getMessage();

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getStatusCode().value())
                .message(message)
                .build();

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    private Map<String, Object> getConstraintViolationAttributes(MethodArgumentNotValidException exception) {
        var constraintViolation = exception.getBindingResult().getAllErrors().stream()
                .findFirst()
                .map(error -> error.unwrap(ConstraintViolation.class))
                .orElseThrow(() -> new IllegalArgumentException("No ConstraintViolation found"));

        return constraintViolation.getConstraintDescriptor().getAttributes();
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        if (attributes == null) return message;

        String minValue = Objects.toString(attributes.get(MIN_ATTRIBUTE), "N/A");
        String maxValue = Objects.toString(attributes.get(MAX_ATTRIBUTE), "N/A");

        message = message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
        message = message.replace("{" + MAX_ATTRIBUTE + "}", maxValue);

        return message;
    }
}
