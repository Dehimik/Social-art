package com.dehimik.art.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String bio;
    private String profilePictureUrl;
    // Додайте інші поля, крім пароля
}
