package com.intern.user.security.entry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intern.user.global.ErrorResponse;
import com.intern.user.global.ErrorResponse.Error;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse body = new ErrorResponse(
            new Error(
                "INVALID_TOKEN",
                "유효하지 않은 토큰입니다."
            )
        );
        objectMapper.writeValue(response.getWriter(), body);
    }
}
