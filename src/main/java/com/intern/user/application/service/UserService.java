package com.intern.user.application.service;

import com.intern.user.application.dto.reqeust.UserLoginRequestDto;
import com.intern.user.application.dto.reqeust.UserSignupRequestDto;
import com.intern.user.application.dto.response.AdminRoleUpdateResponseDto;
import com.intern.user.application.dto.response.UserLoginResponseDto;
import com.intern.user.application.dto.response.UserSignupResponseDto;
import com.intern.user.domain.model.UserRole;

public interface UserService {

    UserSignupResponseDto signupUser(UserSignupRequestDto requestDto);

    UserLoginResponseDto loginUser(UserLoginRequestDto requestDto);

    AdminRoleUpdateResponseDto updateRole(Long userId, UserRole role);
}
