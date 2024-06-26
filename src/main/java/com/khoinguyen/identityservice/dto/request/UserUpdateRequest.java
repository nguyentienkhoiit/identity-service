package com.khoinguyen.identityservice.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.khoinguyen.identityservice.valiator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String firstname;
    String lastname;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

    List<String> roles;
}
