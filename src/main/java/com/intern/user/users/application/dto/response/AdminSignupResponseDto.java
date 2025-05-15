package com.intern.user.users.application.dto.response;

import com.intern.user.users.domain.model.UserRole;
import lombok.Getter;

@Getter
public class AdminSignupResponseDto {

    private final String username;
    private final String nickname;
    private final UserRole role;

    private AdminSignupResponseDto(
        String username,
        String nickname,
        UserRole role
    ) {
        this.username = username;
        this.nickname = nickname;
        this.role = role;
    }

    public static AdminSignupResponseDto of(
        String username,
        String nickname,
        UserRole role
    ) {
        return new AdminSignupResponseDto(
            username,
            nickname,
            role
        );
    }
}
