package com.intern.user.application.mapper;

import com.intern.user.application.dto.reqeust.UserSignupRequestDto;
import com.intern.user.application.dto.response.UserSignupResponseDto;
import com.intern.user.domain.model.User;
import com.intern.user.infrastructure.password.PasswordUtil;

public class UserMapper {

    public static User toUser(UserSignupRequestDto requestDto) {
        String encodedPassword = PasswordUtil.encode(requestDto.getPassword());
        return User.of(requestDto.getUsername(), encodedPassword, requestDto.getNickname());
    }

    public static UserSignupResponseDto toSignupResDto(User user) {
        return UserSignupResponseDto.of(user.getUsername(), user.getNickname(), user.getRole());
    }

}
