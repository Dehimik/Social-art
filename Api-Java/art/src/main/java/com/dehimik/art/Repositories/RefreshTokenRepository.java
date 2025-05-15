package com.dehimik.art.Repositories;

import com.dehimik.art.Entities.RefreshToken;
import com.dehimik.art.Entities.User;

import java.util.Optional;

public interface RefreshTokenRepository extends BaseRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}