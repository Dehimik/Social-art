package com.dehimik.art.dto.auth;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
}

