package com.intern.user.users.application.dto.reqeust;

import lombok.Getter;

@Getter
public class UserSignupRequestDto {

    private final String username;
    private final String password;
    private final String nickname;

    private UserSignupRequestDto(
        String username,
        String password,
        String nickname
    ) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public static UserSignupRequestDto of(
        String username,
        String password,
        String nickname
    ) {
        return new UserSignupRequestDto(
            username,
            password,
            nickname
        );
    }
}
