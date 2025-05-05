package com.dehimik.art.Controllers;


import com.dehimik.art.Entities.User;
import com.dehimik.art.dto.auth.AuthRequest;
import com.dehimik.art.dto.auth.AuthResponse;
import com.dehimik.art.dto.user.UpdateProfileDto;
import com.dehimik.art.dto.user.UserResponseDto;
import com.dehimik.art.exceptions.UserNotFoundException;
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
        UserResponseDto response = userService.getUserDtoById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {
        UserResponseDto user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody AuthRequest request) {
        User newUser = userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse("dummy-token", request.getUsername(), "User registered successfully"));
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<UserResponseDto> updateProfile(
            @PathVariable Long id,
            @RequestBody UpdateProfileDto dto) {
        return ResponseEntity.ok(userService.updateProfile(id, dto));
    }
}