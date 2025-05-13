package com.intern.user.domain.validator;

import com.intern.user.domain.model.UserRole;
import com.intern.user.global.exception.CustomException;
import com.intern.user.global.exception.ErrorCode;

public class UserRoleValidator {

    public static void checkAdmin(UserRole role) {
        if (role.equals(UserRole.USER)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

}
