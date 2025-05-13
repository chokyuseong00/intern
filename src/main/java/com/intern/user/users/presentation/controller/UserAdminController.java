package com.intern.user.users.presentation.controller;

import com.intern.user.users.application.dto.response.AdminRoleUpdateResponseDto;
import com.intern.user.users.application.service.UserService;
import com.intern.user.users.domain.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;

    @PatchMapping("admin/users/{userId}/roles")
    public ResponseEntity<AdminRoleUpdateResponseDto> updateRole(
        @PathVariable(name = "userId") Long userId,
        @RequestHeader(name = "X-User-Role") UserRole role
    ) {
        AdminRoleUpdateResponseDto responseDto = userService.updateRole(userId, role);
        return ResponseEntity.ok(responseDto);
    }
}
