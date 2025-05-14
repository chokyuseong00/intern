package com.intern.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intern.user.security.jwt.JwtProperties;
import com.intern.user.security.jwt.JwtService;
import com.intern.user.users.application.dto.reqeust.UserLoginRequestDto;
import com.intern.user.users.domain.model.User;
import com.intern.user.users.domain.model.UserRole;
import com.intern.user.users.domain.repository.UserRepository;
import com.intern.user.users.infrastructure.password.PasswordUtil;
import com.jayway.jsonpath.JsonPath;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = AutowireMode.ALL)
@DisplayName("관리자 테스트")
@Transactional
public class UserAdminAndTokenTest {

    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final JwtProperties jwtProperties;
    private Long userId;
    private String username;
    private String password;

    private Long adminId;
    private String adminName;
    private String adminPassword;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    public UserAdminAndTokenTest(
        MockMvc mockMvc,
        ObjectMapper objectMapper,
        JwtProperties jwtProperties
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.jwtProperties = jwtProperties;
    }

    @BeforeEach
    void setUp() {
        String encodedUserPassword = PasswordUtil.encode("testPassword");
        User user = User.of(
            "testUsername",
            encodedUserPassword,
            "testNickname"
        );
        userRepository.save(user);
        userId = user.getId();
        username = user.getUsername();
        password = "testPassword";

        String encodedAdminPassword = PasswordUtil.encode("testAdmin");
        User admin = User.of(
            "testAdmin",
            encodedAdminPassword,
            "testAdmin"
        );

        admin.updateRole(UserRole.ADMIN);
        userRepository.save(admin);
        adminId = admin.getId();
        adminName = admin.getUsername();
        adminPassword = "testAdmin";

    }

    @Test
    @DisplayName("사용자 권한 부여 : 성공")
    void updateUserRoleSuccess() throws Exception {
        UserLoginRequestDto requestDto = UserLoginRequestDto.of(
            adminName,
            adminPassword
        );

        MvcResult adminLogin = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
            )
            .andReturn();

        String token = JsonPath.read(adminLogin.getResponse().getContentAsString(), "token");

        ResultActions resultActions = mockMvc.perform(patch("/admin/users/{userId}/roles", userId)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        );

        resultActions.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("role").value(String.valueOf(UserRole.ADMIN)));
    }

    @Test
    @DisplayName("사용자 권한 부여 : 권한 없음 실패")
    void updateUserRoleFail() throws Exception {
        UserLoginRequestDto requestDto = UserLoginRequestDto.of(
            username,
            password
        );

        MvcResult userLogin = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
            )
            .andReturn();

        String token = JsonPath.read(userLogin.getResponse().getContentAsString(), "token");

        ResultActions resultActions = mockMvc.perform(patch("/admin/users/{userId}/roles", userId)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        );

        resultActions.andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("error.code").value("UNAUTHORIZED_ACCESS"))
            .andExpect(jsonPath("error.message").value("권한이 없습니다."));
    }

    @Test
    @DisplayName("토큰 검증 : 만료된 토큰 실패")
    void expiredTokenFailTest() {
        Key key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
        Date now = new Date();

        String expiredToken = Jwts.builder()
            .setSubject(String.valueOf(adminId))
            .claim("username", adminName)
            .claim("role", UserRole.ADMIN)
            .setIssuedAt(now)
            .setExpiration(now)
            .signWith(key, signatureAlgorithm)
            .compact();

        JwtException ex = assertThrows(
            JwtException.class,
            () -> jwtService.validateToken(expiredToken)
        );
        assertThat(ex.getMessage()).isEqualTo("만료된 토큰입니다");
    }

    @Test
    @DisplayName("토큰 검증 : 유효하지 않은 토큰")
    void validTokenTest() {
        String inValidToken = "is.not.token";

        JwtException ex = assertThrows(
            JwtException.class,
            () -> jwtService.validateToken(inValidToken)
        );
        assertThat(ex.getMessage()).isEqualTo("유효하지 않은 토큰입니다");
    }

}
