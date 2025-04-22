package com.dehimik.art.services;

import com.dehimik.art.Entities.User;
import com.dehimik.art.Repositories.UserRepository;
import com.dehimik.art.exceptions.EmailExistsException;
import com.dehimik.art.exceptions.UserNotFoundException;
import com.dehimik.art.exceptions.UsernameExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @Transactional
    public User updateProfile(Long userId, String newBio, String newProfilePictureUrl) {
        User user = getUserById(userId);
        if (newBio != null) {
            user.setBio(newBio);
        }
        if (newProfilePictureUrl != null) {
            user.setProfilePictureUrl(newProfilePictureUrl);
        }
        return userRepository.save(user);
    }
}
