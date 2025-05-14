package com.intern.user.security.config;

import com.intern.user.security.entry.CustomEntryPoint;
import com.intern.user.security.filter.JwtAuthenticationFilter;
import com.intern.user.security.handler.CustomAccessHandler;
import com.intern.user.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http
    ) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService);
        CustomEntryPoint customEntryPoint = new CustomEntryPoint();
        CustomAccessHandler customAccessHandler = new CustomAccessHandler();

        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(customEntryPoint)
                .accessDeniedHandler(customAccessHandler)
            );

        http
            .authorizeHttpRequests(authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers(
                        "/signup",
                        "/login",
                        "/admin/users/signup",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**"
                    ).permitAll()

                    .requestMatchers(HttpMethod.PATCH,
                        "/admin/**"
                    ).hasAnyAuthority("ADMIN")
                    .anyRequest().authenticated()
            )
            .addFilterAfter(jwtAuthenticationFilter, SecurityContextHolderFilter.class);

        return http.build();
    }
}
