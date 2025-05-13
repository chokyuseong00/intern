package com.intern.user.application.dto.response;

import com.intern.user.domain.model.UserRole;
import lombok.Getter;

@Getter
public class UserSignupResponseDto {

    private final String username;
    private final String nickname;
    private final UserRole role;

    private UserSignupResponseDto(
        String username,
        String nickname,
        UserRole role
    ) {
        this.username = username;
        this.nickname = nickname;
        this.role = role;
    }

    public static UserSignupResponseDto of(
        String username,
        String nickname,
        UserRole role
    ) {
        return new UserSignupResponseDto(
            username,
            nickname,
            role
        );
    }
}
