package com.khoinguyen.identityservice.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import com.khoinguyen.identityservice.valiator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, max = 32, message = "USERNAME_INVALID")
    String username;

    @Size(min = 4, max = 32, message = "PASSWORD_INVALID")
    String password;

    String firstname;
    String lastname;

    @DobConstraint(min = 16, message = "INVALID_DOB")
    LocalDate dob;
}
