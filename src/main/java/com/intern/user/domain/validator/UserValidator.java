package com.intern.user.domain.validator;

import com.intern.user.domain.repository.UserRepository;
import com.intern.user.global.exception.CustomException;
import com.intern.user.global.exception.ErrorCode;
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
}
