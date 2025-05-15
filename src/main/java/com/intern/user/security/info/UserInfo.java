package com.intern.user.security.info;

import io.jsonwebtoken.Claims;
import java.time.Instant;

public record UserInfo(
    String userId,
    String username,
    String role,
    Instant expTime
) {

    public static UserInfo ofClaims(Claims claims) {
        return new UserInfo(
            claims.getSubject(),
            claims.get("username", String.class),
            claims.get("role", String.class),
            claims.getExpiration().toInstant()
        );
    }
}
