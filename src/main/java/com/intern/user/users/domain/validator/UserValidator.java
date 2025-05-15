package com.intern.user.users.domain.validator;

import com.intern.user.global.exception.CustomException;
import com.intern.user.global.exception.ErrorCode;
import com.intern.user.users.application.dto.reqeust.UserLoginRequestDto;
import com.intern.user.users.application.dto.reqeust.UserSignupRequestDto;
import com.intern.user.users.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void usernameValidate(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new CustomException(ErrorCode.USER_ALREADY_EXISTS);
        }
    }

    public void signupValidate(UserSignupRequestDto requestDto) {
        if (requestDto.getUsername() == null || requestDto.getUsername().isEmpty()) {
            throw new CustomException(ErrorCode.USERNAME_INVALID);
        }
        if (requestDto.getPassword() == null || requestDto.getPassword().isEmpty()) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID);
        }
        if (requestDto.getNickname() == null || requestDto.getNickname().isEmpty()) {
            throw new CustomException(ErrorCode.NICKNAME_INVALID);
        }
    }

    public void loginValidator(UserLoginRequestDto requestDto) {
        if (requestDto.getUsername() == null || requestDto.getUsername().isEmpty()) {
            throw new CustomException(ErrorCode.USERNAME_INVALID);
        }
        if (requestDto.getPassword() == null || requestDto.getPassword().isEmpty()) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID);
        }
    }

}
