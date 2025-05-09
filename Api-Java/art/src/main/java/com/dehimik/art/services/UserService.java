package com.dehimik.art.services;

import com.dehimik.art.Entities.User;
import com.dehimik.art.Repositories.BaseRepository;
import com.dehimik.art.Repositories.UserRepository;
import com.dehimik.art.dto.user.UpdateProfileDto;
import com.dehimik.art.dto.user.UserResponseDto;
import com.dehimik.art.exceptions.EmailExistsException;
import com.dehimik.art.exceptions.UserNotFoundException;
import com.dehimik.art.exceptions.UsernameExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService<User, Long>{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private UserResponseDto convertToDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getBio(),
                user.getProfilePictureUrl()
        );
    }

    public Long getUserIdFromPrincipal(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Користувач не аутентифікований");
        }

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Користувач не знайдений"));

        return user.getId();
    }

    @Transactional
    public User registerUser(String username, String email, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameExistsException(username);
        }
        if (userRepository.existsByEmail(email)) {
            throw new EmailExistsException(email);
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    public UserResponseDto getUserDtoById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        UserMapper userMapper = new UserMapper();
        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        UserMapper userMapper = new UserMapper();
        return userMapper.toDto(user);
    }

    @Transactional
    public UserResponseDto updateProfile(Long userId, UpdateProfileDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (request.getBio() != null && !request.getBio().isEmpty()) {
            user.setBio(request.getBio());
        }

        if (request.getProfilePictureUrl() != null && !request.getProfilePictureUrl().isEmpty()) {
            user.setProfilePictureUrl(request.getProfilePictureUrl());
        }

        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }

    @Override
    protected BaseRepository<User, Long> getRepository() {
        return userRepository;
    }
}
