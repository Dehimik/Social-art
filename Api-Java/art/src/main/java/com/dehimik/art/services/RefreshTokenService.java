package com.dehimik.art.services;

import com.dehimik.art.Entities.RefreshToken;
import com.dehimik.art.Entities.User;
import com.dehimik.art.Repositories.RefreshTokenRepository;
import com.dehimik.art.Repositories.UserRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository repo;
    private final UserRepository userRepo;
    @Value("${jwt.refreshExpirationMs}") private long refreshDurationMs;

    public RefreshToken create(User user) {
        RefreshToken rt = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshDurationMs))
                .build();
        return repo.save(rt);
    }

    public RefreshToken verify(String token) {
        RefreshToken rt = repo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));
        if (rt.getExpiryDate().isBefore(Instant.now())) {
            repo.delete(rt);
            throw new RuntimeException("Refresh token expired");
        }
        return rt;
    }

    public void deleteByUser(User user) {
        repo.deleteByUser(user);
    }
}

