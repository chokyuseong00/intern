package com.intern.user.presentation.controller;

import com.intern.user.application.dto.reqeust.UserLoginRequestDto;
import com.intern.user.application.dto.reqeust.UserSignupRequestDto;
import com.intern.user.application.dto.response.UserLoginResponseDto;
import com.intern.user.application.dto.response.UserSignupResponseDto;
import com.intern.user.application.service.UserService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponseDto> signupUser(
        @RequestBody UserSignupRequestDto requestDto
    ) {
        UserSignupResponseDto responseDto = userService.signupUser(requestDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/v1/users/my-page")
            .build()
            .toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> loginUser(
        @RequestBody UserLoginRequestDto requestDto
    ) {
        UserLoginResponseDto responseDto = userService.loginUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
