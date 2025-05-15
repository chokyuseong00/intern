package com.intern.user.users.application.mapper;

import com.intern.user.users.application.dto.reqeust.AdminSignupRequestDto;
import com.intern.user.users.application.dto.reqeust.UserSignupRequestDto;
import com.intern.user.users.application.dto.response.AdminRoleUpdateResponseDto;
import com.intern.user.users.application.dto.response.AdminSignupResponseDto;
import com.intern.user.users.application.dto.response.UserLoginResponseDto;
import com.intern.user.users.application.dto.response.UserSignupResponseDto;
import com.intern.user.users.domain.model.User;
import com.intern.user.users.infrastructure.password.PasswordUtil;

public class UserMapper {

    public static User toUser(UserSignupRequestDto requestDto) {
        String encodedPassword = PasswordUtil.encode(requestDto.getPassword());
        return User.of(requestDto.getUsername(), encodedPassword, requestDto.getNickname());
    }

    public static UserSignupResponseDto toSignupResDto(User user) {
        return UserSignupResponseDto.of(user.getUsername(), user.getNickname(), user.getRole());
    }

    public static UserLoginResponseDto toLoginResDto(String token) {
        return UserLoginResponseDto.of(token);
    }

    public static AdminRoleUpdateResponseDto toUpdateRoleResDto(User user) {
        return AdminRoleUpdateResponseDto.of(user.getUsername(), user.getNickname(),
            user.getRole());
    }

    public static User toAdmin(AdminSignupRequestDto requestDto) {
        String encodedPassword = PasswordUtil.encode(requestDto.getPassword());
        return User.of(requestDto.getUsername(), encodedPassword, requestDto.getNickname());
    }

    public static AdminSignupResponseDto toSignupAdminResDto(User admin) {
        return AdminSignupResponseDto.of(admin.getUsername(), admin.getNickname(), admin.getRole());
    }
}
