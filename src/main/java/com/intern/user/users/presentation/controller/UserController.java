package com.intern.user.users.presentation.controller;

import com.intern.user.users.application.dto.reqeust.UserLoginRequestDto;
import com.intern.user.users.application.dto.reqeust.UserSignupRequestDto;
import com.intern.user.users.application.dto.response.UserLoginResponseDto;
import com.intern.user.users.application.dto.response.UserSignupResponseDto;
import com.intern.user.users.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "일반 사용자 회원 가입", description = "일반 사용자의 회원가입 API 입니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "201",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserSignupResponseDto.class)
            )
        )
    })
    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponseDto> signupUser(
        @RequestBody UserSignupRequestDto requestDto
    ) {
        UserSignupResponseDto responseDto = userService.signupUser(requestDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/users/my-page")
            .build()
            .toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }

    @Operation(summary = "로그인", description = "사용자 로그인 API 입니다.")
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> loginUser(
        @RequestBody UserLoginRequestDto requestDto
    ) {
        UserLoginResponseDto responseDto = userService.loginUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
