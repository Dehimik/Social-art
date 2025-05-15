package com.dehimik.art.services;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.accessSecret}")     private String accessSecret;
    @Value("${jwt.refreshSecret}")    private String refreshSecret;
    @Value("${jwt.accessExpirationMs}")  private long accessExpMs;
    @Value("${jwt.refreshExpirationMs}") private long refreshExpMs;

    public String generateAccessToken(UserDetails u) {
        return buildToken(u.getUsername(), accessExpMs, accessSecret);
    }
    public String generateRefreshToken(UserDetails u) {
        return buildToken(u.getUsername(), refreshExpMs, refreshSecret);
    }
    private String buildToken(String subject, long expMs, String secret) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(expMs)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    public String getUsernameFromAccess(String token) {
        return Jwts.parser().setSigningKey(accessSecret)
                .parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateAccess(String token) {
        try { Jwts.parser().setSigningKey(accessSecret).parseClaimsJws(token); return true; }
        catch (JwtException e) { return false; }
    }
    public boolean validateRefresh(String token) {
        try { Jwts.parser().setSigningKey(refreshSecret).parseClaimsJws(token); return true; }
        catch (JwtException e) { return false; }
    }
}

