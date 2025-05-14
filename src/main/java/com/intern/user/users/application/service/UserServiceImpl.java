package com.intern.user.users.application.service;

import com.intern.user.security.jwt.JwtService;
import com.intern.user.users.application.dto.reqeust.AdminSignupRequestDto;
import com.intern.user.users.application.dto.reqeust.UserLoginRequestDto;
import com.intern.user.users.application.dto.reqeust.UserSignupRequestDto;
import com.intern.user.users.application.dto.response.AdminRoleUpdateResponseDto;
import com.intern.user.users.application.dto.response.AdminSignupResponseDto;
import com.intern.user.users.application.dto.response.UserLoginResponseDto;
import com.intern.user.users.application.dto.response.UserSignupResponseDto;
import com.intern.user.users.application.mapper.UserMapper;
import com.intern.user.users.application.validator.AdminValidator;
import com.intern.user.users.domain.model.User;
import com.intern.user.users.domain.model.UserRole;
import com.intern.user.users.domain.repository.UserRepository;
import com.intern.user.users.domain.validator.UserRoleValidator;
import com.intern.user.users.domain.validator.UserValidator;
import com.intern.user.users.infrastructure.password.PasswordUtil;
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

    @Override
    @Transactional
    public AdminRoleUpdateResponseDto updateRole(Long userId, UserRole role) {
        UserRoleValidator.checkAdmin(role);
        User user = userRepository.findById(userId);
        user.updateRole(UserRole.ADMIN);
        return UserMapper.toUpdateRoleResDto(user);
    }

    @Override
    @Transactional
    public AdminSignupResponseDto signupAdmin(AdminSignupRequestDto requestDto) {
        userValidator.usernameValidate(requestDto.getUsername());
        AdminValidator.checkPin(requestDto.getAdminPin());
        User admin = UserMapper.toAdmin(requestDto);
        userRepository.save(admin);
        return UserMapper.toSignupAdminResDto(admin);
    }


}
