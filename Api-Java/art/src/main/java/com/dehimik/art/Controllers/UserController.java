package com.dehimik.art.Controllers;


import com.dehimik.art.Entities.User;
import com.dehimik.art.dto.auth.AuthRequest;
import com.dehimik.art.dto.auth.AuthResponse;
import com.dehimik.art.dto.user.UpdateProfileDto;
import com.dehimik.art.dto.user.UserResponseDto;
import com.dehimik.art.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody AuthRequest request) {
        User newUser = userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse("dummy-token", request.getUsername(), "User registered successfully"));
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<UserResponseDto> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateProfileDto request) {

        UserResponseDto response = userService.updateProfile(userId, request);
        return ResponseEntity.ok(response);
    }
}