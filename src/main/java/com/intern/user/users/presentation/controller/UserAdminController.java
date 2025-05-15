package com.intern.user.users.presentation.controller;

import com.intern.user.users.application.dto.reqeust.AdminSignupRequestDto;
import com.intern.user.users.application.dto.response.AdminRoleUpdateResponseDto;
import com.intern.user.users.application.dto.response.AdminSignupResponseDto;
import com.intern.user.users.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;

    @Operation(summary = "관리자 회원가입", description = "관리자 회원가입 API 입니다. 관리자 PIN 번호가 필요합니다. adminPin : admin1234")
    @ApiResponses({
        @ApiResponse(responseCode = "201",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AdminSignupResponseDto.class)
            )
        )
    })
    @PostMapping("/admin/users/signup")
    public ResponseEntity<AdminSignupResponseDto> signupAdmin(
        @RequestBody AdminSignupRequestDto requestDto
    ) {
        AdminSignupResponseDto responseDto = userService.signupAdmin(requestDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/users/my-page")
            .build()
            .toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }

    @Operation(summary = "관리자 권한 부여", description = "관리자 권한 부여 API 입니다. 관리자 권한을 가진 사용자만 접근할 수 있습니다.")
    @PatchMapping("/admin/users/{userId}/roles")
    public ResponseEntity<AdminRoleUpdateResponseDto> updateRole(
        @PathVariable(name = "userId") Long userId
    ) {
        AdminRoleUpdateResponseDto responseDto = userService.updateRole(userId);
        return ResponseEntity.ok(responseDto);
    }
}
