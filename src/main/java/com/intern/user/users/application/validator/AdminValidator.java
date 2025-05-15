package com.intern.user.users.application.validator;

import com.intern.user.global.exception.CustomException;
import com.intern.user.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdminValidator {

    @Value("${admin.pin}")
    private String pin;

    public void checkPin(String adminPin) {
        if (adminPin == null || adminPin.isEmpty()) {
            throw new CustomException(ErrorCode.ADMIN_PIN_NULL);
        }
        if (!adminPin.equals(pin)) {
            throw new CustomException(ErrorCode.INVALID_ADMIN_PIN);
        }
    }
}
