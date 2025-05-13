package com.intern.user.security.filter;

import com.intern.user.security.header.HeaderUtil;
import com.intern.user.security.info.UserInfo;
import com.intern.user.security.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtService.getAccessToken(authHeader);
        jwtService.validateToken(accessToken);
        UserInfo userInfo = jwtService.createUserInfo(accessToken);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userInfo.username(),
            null,
            Collections.singletonList(new SimpleGrantedAuthority(userInfo.role()))
        );
        authenticationToken.setDetails(userInfo.userId());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        HttpServletRequest wrapped = HeaderUtil.createCustomHeader(request, userInfo);
        filterChain.doFilter(wrapped, response);
    }

}
