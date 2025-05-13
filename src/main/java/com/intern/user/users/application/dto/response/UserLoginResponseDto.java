package com.intern.user.users.application.dto.response;

import lombok.Getter;

@Getter
public class UserLoginResponseDto {

    private final String token;

    private UserLoginResponseDto(
        String token
    ) {
        this.token = token;
    }

    public static UserLoginResponseDto of(
        String token
    ) {
        return new UserLoginResponseDto(
            token
        );
    }
}
