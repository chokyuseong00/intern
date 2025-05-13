package com.intern.user.infrastructure.password;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {

    public static String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

}
