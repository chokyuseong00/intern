package com.intern.user.application.service;

import com.intern.user.application.dto.reqeust.UserSignupRequestDto;
import com.intern.user.application.dto.response.UserSignupResponseDto;
import com.intern.user.application.mapper.UserMapper;
import com.intern.user.domain.model.User;
import com.intern.user.domain.repository.UserRepository;
import com.intern.user.domain.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Override
    @Transactional
    public UserSignupResponseDto signupUser(UserSignupRequestDto requestDto) {
        userValidator.usernameValidate(requestDto.getUsername());
        User user = UserMapper.toUser(requestDto);
        userRepository.save(user);
        return UserMapper.toSignupResDto(user);
    }
}
