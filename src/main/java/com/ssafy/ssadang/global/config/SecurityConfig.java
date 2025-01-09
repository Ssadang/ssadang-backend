package com.ssafy.ssadang.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ssafy.ssadang.global.security.JwtAccessDeniedHandler;
import com.ssafy.ssadang.global.security.JwtAuthenticationEntryPoint;
import com.ssafy.ssadang.global.security.filter.JwtFilter;

import lombok.RequiredArgsConstructor;

// 인가 및 설정을 담당
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // final이 붙여져있는 객체에 자동으로 autowired를 해줌 
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtFilter jwtFilter;
    private final String[] adminUrl = {"/admin/**"};
    private final String[] permitAllUrl = {"/error", "/user/login", "/user/signup", "/user/reissue", "/api/v1/chat/**", "/api/v1/notifications/**", "/api/v1/notifications","/ws-connect/**"};
    private final String[] temporaryUrl = {"/user/test"};

	// 비밀번호 암호화 메서드
	// 단방향 암호화
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(handle -> handle
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(adminUrl).hasAnyRole("ADMIN")
                        .requestMatchers(permitAllUrl).permitAll()
                        .requestMatchers(temporaryUrl).hasAnyRole("TEMPORARY")
                        .anyRequest().authenticated()
                )
                .build();
	}
}
