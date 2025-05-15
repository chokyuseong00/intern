package com.intern.user.users.presentation.controller;

import com.intern.user.users.application.dto.reqeust.AdminSignupRequestDto;
import com.intern.user.users.application.dto.response.AdminRoleUpdateResponseDto;
import com.intern.user.users.application.dto.response.AdminSignupResponseDto;
import com.intern.user.users.application.service.UserService;
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

    @PatchMapping("/admin/users/{userId}/roles")
    public ResponseEntity<AdminRoleUpdateResponseDto> updateRole(
        @PathVariable(name = "userId") Long userId
    ) {
        AdminRoleUpdateResponseDto responseDto = userService.updateRole(userId);
        return ResponseEntity.ok(responseDto);
    }
}
