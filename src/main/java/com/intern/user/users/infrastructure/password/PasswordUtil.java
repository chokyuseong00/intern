package com.intern.user.users.infrastructure.password;

import com.intern.user.global.exception.CustomException;
import com.intern.user.global.exception.ErrorCode;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {

    public static String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public static void isMatched(String rawPassword, String password) {
        if (!BCrypt.checkpw(rawPassword, password)) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }
    }
}
