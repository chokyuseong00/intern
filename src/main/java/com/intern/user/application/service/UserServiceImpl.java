package com.intern.user.application.service;

import com.intern.user.application.dto.reqeust.UserLoginRequestDto;
import com.intern.user.application.dto.reqeust.UserSignupRequestDto;
import com.intern.user.application.dto.response.UserLoginResponseDto;
import com.intern.user.application.dto.response.UserSignupResponseDto;
import com.intern.user.application.mapper.UserMapper;
import com.intern.user.domain.model.User;
import com.intern.user.domain.repository.UserRepository;
import com.intern.user.domain.validator.UserValidator;
import com.intern.user.infrastructure.password.PasswordUtil;
import com.intern.user.infrastructure.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final JwtService jwtService;

    @Override
    @Transactional
    public UserSignupResponseDto signupUser(UserSignupRequestDto requestDto) {
        userValidator.usernameValidate(requestDto.getUsername());
        User user = UserMapper.toUser(requestDto);
        userRepository.save(user);
        return UserMapper.toSignupResDto(user);
    }

    @Override
    public UserLoginResponseDto loginUser(UserLoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername());
        PasswordUtil.isMatched(requestDto.getPassword(), user.getPassword());

        String accessToken = jwtService.createAccessToken(user);
        return UserMapper.toLoginResDto(accessToken);
    }
}
