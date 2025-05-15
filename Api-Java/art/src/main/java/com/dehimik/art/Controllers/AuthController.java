package com.dehimik.art.Controllers;

import com.dehimik.art.Entities.RefreshToken;
import com.dehimik.art.Entities.User;
import com.dehimik.art.Repositories.UserRepository;
import com.dehimik.art.dto.auth.JwtResponse;
import com.dehimik.art.dto.auth.LoginRequest;
import com.dehimik.art.dto.auth.SignupRequest;
import com.dehimik.art.dto.auth.TokenRefreshRequest;
import com.dehimik.art.services.JwtService;
import com.dehimik.art.services.RefreshTokenService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final RefreshTokenService rtService;
    private final UserRepository userRepo;
    private final PasswordEncoder pwdEnc;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest req) {
        if (userRepo.existsByUsername(req.getUsername()))
            return bad("Username taken");
        if (userRepo.existsByEmail(req.getEmail()))
            return bad("Email in use");

        User u = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .passwordHash(pwdEnc.encode(req.getPassword()))
                .build();
        userRepo.save(u);
        return ResponseEntity.ok(Map.of("message","User registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest req) {
        Authentication a = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        String access = jwtService.generateAccessToken((UserDetails) a.getPrincipal());
        String refresh = jwtService.generateRefreshToken((UserDetails) a.getPrincipal());
        RefreshToken rt = rtService.create(
                userRepo.findByUsername(req.getUsername()).get()
        );
        return ResponseEntity.ok(new JwtResponse(access, refresh));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody TokenRefreshRequest req) {
        RefreshToken rt = rtService.verify(req.getRefreshToken());
        String username = jwtService.getUsernameFromAccess(rt.getToken());
        UserDetails ud = new org.springframework.security.core.userdetails.User(
                username, "", List.of()
        );
        String newAccess = jwtService.generateAccessToken(ud);
        return ResponseEntity.ok(new JwtResponse(newAccess, rt.getToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenRefreshRequest req) {
        RefreshToken rt = rtService.verify(req.getRefreshToken());
        rtService.deleteByUser(rt.getUser());
        return ResponseEntity.ok(Map.of("message","Logged out"));
    }

    private ResponseEntity<Map<String,String>> bad(String msg) {
        return ResponseEntity.badRequest().body(Map.of("error", msg));
    }
}
