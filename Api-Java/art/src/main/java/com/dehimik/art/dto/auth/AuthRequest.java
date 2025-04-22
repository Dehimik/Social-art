package com.dehimik.art.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank
    private String username;

    @Email
    private String email;

    @Size(min = 6)
    private String password;
}