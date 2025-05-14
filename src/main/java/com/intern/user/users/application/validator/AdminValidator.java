package com.intern.user.users.application.validator;

import com.intern.user.global.exception.CustomException;
import com.intern.user.global.exception.ErrorCode;

public class AdminValidator {

    private static final String ADMIN_PIN = "admin1234";

    public static void checkPin(String adminPin) {
        if (adminPin == null || adminPin.isEmpty()) {
            throw new CustomException(ErrorCode.ADMIN_PIN_NULL);
        }
        if (!adminPin.equals(ADMIN_PIN)) {
            throw new CustomException(ErrorCode.INVALID_ADMIN_PIN);
        }
    }
}
