package com.intern.user.infrastructure.security.jwt;

import com.intern.user.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final JwtProperties jwtProperties;

    private Key key;

    @PostConstruct
    public void ketInit() {
        key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public void validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new JwtException("토큰이 null 이거나 비어 있습니다");
        }
        try {
            getClaims(token);
        } catch (ExpiredJwtException e) {
            throw new JwtException("만료된 토큰입니다", e);
        } catch (JwtException e) {
            throw new JwtException("유효하지 않은 토큰입니다", e);
        }
    }

    public String createAccessToken(User user) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtProperties.getAccessExp());

        return Jwts.builder()
            .setSubject(String.valueOf(user.getId()))
            .claim("username", user.getUsername())
            .claim("role", user.getRole())
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(key, signatureAlgorithm)
            .compact();
    }
}
