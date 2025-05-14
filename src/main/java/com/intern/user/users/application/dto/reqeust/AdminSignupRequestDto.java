package com.intern.user.users.application.dto.reqeust;

import lombok.Getter;

@Getter
public class AdminSignupRequestDto {

    private final String username;
    private final String password;
    private final String nickname;
    private final String adminPin;

    private AdminSignupRequestDto(
        String username,
        String password,
        String nickname,
        String adminPin
    ) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.adminPin = adminPin;
    }

    public static AdminSignupRequestDto of(
        String username,
        String password,
        String nickname,
        String adminPin
    ) {
        return new AdminSignupRequestDto(
            username,
            password,
            nickname,
            adminPin
        );
    }
}
