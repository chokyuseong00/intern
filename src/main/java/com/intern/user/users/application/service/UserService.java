package com.intern.user.users.application.service;

import com.intern.user.users.application.dto.reqeust.AdminSignupRequestDto;
import com.intern.user.users.application.dto.reqeust.UserLoginRequestDto;
import com.intern.user.users.application.dto.reqeust.UserSignupRequestDto;
import com.intern.user.users.application.dto.response.AdminRoleUpdateResponseDto;
import com.intern.user.users.application.dto.response.AdminSignupResponseDto;
import com.intern.user.users.application.dto.response.UserLoginResponseDto;
import com.intern.user.users.application.dto.response.UserSignupResponseDto;
import com.intern.user.users.domain.model.UserRole;

public interface UserService {

    UserSignupResponseDto signupUser(UserSignupRequestDto requestDto);

    UserLoginResponseDto loginUser(UserLoginRequestDto requestDto);

    AdminRoleUpdateResponseDto updateRole(Long userId, UserRole role);

    AdminSignupResponseDto signupAdmin(AdminSignupRequestDto requestDto);
}
