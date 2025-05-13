package com.intern.user.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intern.user.global.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(
        HttpServletRequest request,
        HttpServletResponse response,
        AccessDeniedException accessDeniedException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse body = new ErrorResponse(
            new ErrorResponse.Error(
                "ACCESS_DENIED",
                "관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다."
            )
        );
        mapper.writeValue(response.getWriter(), body);
    }
}
