package com.intern.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intern.user.security.info.UserInfo;
import com.intern.user.security.jwt.JwtService;
import com.intern.user.users.application.dto.reqeust.UserLoginRequestDto;
import com.intern.user.users.application.dto.reqeust.UserSignupRequestDto;
import com.intern.user.users.domain.model.User;
import com.intern.user.users.domain.model.UserRole;
import com.intern.user.users.domain.repository.UserRepository;
import com.intern.user.users.infrastructure.password.PasswordUtil;
import com.jayway.jsonpath.JsonPath;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = AutowireMode.ALL)
@DisplayName("사용자 테스트")
@Transactional
public class UserTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    private final JwtService jwtService;

    private Long userId;
    private String username;
    private String password;
    private String nickname;

    @Autowired
    private UserRepository userRepository;

    public UserTest(
        MockMvc mockMvc,
        ObjectMapper objectMapper,
        JwtService jwtService
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
    }

    @BeforeEach
    void setUp() {
        String encodedPassword = PasswordUtil.encode("testPassword");
        User user = User.of(
            "testUsername",
            encodedPassword,
            "testNickname"
        );
        userRepository.save(user);
        userId = user.getId();
        username = user.getUsername();
        password = "testPassword";
        nickname = user.getNickname();
    }

    @Test
    @DisplayName("회원가입 테스트 : 성공")
    void signupUserTestSuccess() throws Exception {
        UserSignupRequestDto requestDto = UserSignupRequestDto.of(
            "testUsername1",
            "testPassword1",
            "testNickname1"
        );

        ResultActions resultActions = mockMvc.perform(post("/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto))
        );

        resultActions.andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("username").value(requestDto.getUsername()))
            .andExpect(jsonPath("nickname").value(requestDto.getNickname()))
            .andExpect(header().exists(HttpHeaders.LOCATION));
    }

    @Test
    @DisplayName("회원가입 테스트 : 중복 회원가입 실패")
    void signupUserTestFail() throws Exception {
        UserSignupRequestDto requestDto = UserSignupRequestDto.of(
            username,
            password,
            nickname
        );

        ResultActions resultActions = mockMvc.perform(post("/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto))
        );

        resultActions.andDo(print())
            .andExpect(status().isConflict())
            .andExpect(jsonPath("error.code").value("USER_ALREADY_EXISTS"))
            .andExpect(jsonPath("error.message").value("이미 가입된 사용자입니다."));
    }

    @Test
    @DisplayName("로그인 테스트 : 성공")
    void loginUserTestSuccess() throws Exception {
        UserLoginRequestDto requestDto = UserLoginRequestDto.of(
            username,
            password
        );

        ResultActions resultActions = mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto))
        );

        resultActions.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("token", not(emptyOrNullString())));

    }

    @Test
    @DisplayName("로그인 테스트 : 비밀번호 불일치 실패")
    void loginUserTestFailPassword() throws Exception {
        UserLoginRequestDto requestDto = UserLoginRequestDto.of(
            username,
            "failPassword"
        );

        ResultActions resultActions = mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto))
        );

        resultActions.andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("error.code").value("INVALID_CREDENTIALS"))
            .andExpect(jsonPath("error.message").value("아이디 또는 비밀번호가 올바르지 않습니다."));
    }

    @Test
    @DisplayName("토큰 검증 테스트 : 성공")
    void tokenTestSuccess() throws Exception {
        UserLoginRequestDto requestDto = UserLoginRequestDto.of(
            username,
            password
        );

        ResultActions resultActions = mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto))
        );

        resultActions.andDo(print())
            .andExpect(jsonPath("token", not(emptyOrNullString())))
            .andExpect(result -> {
                String responseBody = result.getResponse().getContentAsString();
                String token = JsonPath.read(responseBody, "token");
                UserInfo userInfo = jwtService.createUserInfo(token);
                Instant now = Instant.now();
                Instant exp = userInfo.expTime();
                assertThat(userInfo.userId()).isEqualTo(String.valueOf(userId));
                assertThat(userInfo.username()).isEqualTo(username);
                assertThat(userInfo.role()).isEqualTo(String.valueOf(UserRole.USER));
                assertThat(exp).isAfter(now);
                assertThat(exp).isBefore(now.plus(1, ChronoUnit.HOURS));
            });
    }

}
