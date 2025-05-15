package com.intern.user.users.application.dto.reqeust;

import lombok.Getter;

@Getter
public class UserLoginRequestDto {

    private final String username;
    private final String password;

    private UserLoginRequestDto(
        String username,
        String password
    ) {
        this.username = username;
        this.password = password;
    }

    public static UserLoginRequestDto of(
        String username,
        String password
    ) {
        return new UserLoginRequestDto(
            username,
            password
        );
    }

}
