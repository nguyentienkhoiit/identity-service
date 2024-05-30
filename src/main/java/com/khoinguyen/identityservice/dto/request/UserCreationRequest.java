package com.khoinguyen.identityservice.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, max = 32, message = "USERNAME_INVALID")
    String username;
    @Size(min = 8, max = 32, message = "PASSWORD_INVALID")
    String password;
    String firstname;
    String lastname;
    LocalDate dob;
}
