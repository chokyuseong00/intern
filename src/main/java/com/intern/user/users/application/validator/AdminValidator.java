package com.intern.user.users.application.validator;

import com.intern.user.global.exception.CustomException;
import com.intern.user.global.exception.ErrorCode;
import com.intern.user.users.application.dto.reqeust.AdminSignupRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdminValidator {

    @Value("${admin.pin}")
    private String pin;

    public void adminSignupValidate(AdminSignupRequestDto requestDto) {
        if (requestDto.getUsername() == null || requestDto.getUsername().isEmpty()) {
            throw new CustomException(ErrorCode.USERNAME_INVALID);
        }
        if (requestDto.getPassword() == null || requestDto.getPassword().isEmpty()) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID);
        }
        if (requestDto.getNickname() == null || requestDto.getNickname().isEmpty()) {
            throw new CustomException(ErrorCode.NICKNAME_INVALID);
        }
        if (requestDto.getAdminPin() == null || requestDto.getAdminPin().isEmpty()) {
            throw new CustomException(ErrorCode.ADMIN_PIN_NULL);
        }
        if (!requestDto.getAdminPin().equals(pin)) {
            throw new CustomException(ErrorCode.INVALID_ADMIN_PIN);
        }
    }

}
