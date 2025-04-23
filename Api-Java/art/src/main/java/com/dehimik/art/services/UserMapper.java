package com.dehimik.art.services;

import com.dehimik.art.Entities.User;
import com.dehimik.art.dto.user.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getBio(),
                user.getProfilePictureUrl()
        );
    }
}
