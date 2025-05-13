package com.intern.user.application.service;

import com.intern.user.application.dto.reqeust.UserSignupRequestDto;
import com.intern.user.application.dto.response.UserSignupResponseDto;

public interface UserService {

    UserSignupResponseDto signupUser(UserSignupRequestDto requestDto);
}
