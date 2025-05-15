package com.dehimik.art.dto.auth;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {
    @NotBlank
    private String username;

    @Email @NotBlank
    private String email;

    @NotBlank @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
