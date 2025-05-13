package com.intern.user.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.intern.user.users.domain.model.User;
import com.intern.user.users.domain.model.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DisplayName("User 엔티티 테스트")
public class UserEntityTest {

    @Test
    @DisplayName("사용자 엔티티 생성 테스트")
    void userOfTest() {
        String username = "testUser";
        String password = "testPassword";
        String nickname = "testNickname";

        User user = User.of(username, password, nickname);

        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(UserRole.USER, user.getRole());
    }
}
