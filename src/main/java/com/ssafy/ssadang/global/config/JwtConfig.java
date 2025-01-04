package com.ssafy.ssadang.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ssafy.ssadang.global.security.JwtAccessDeniedHandler;
import com.ssafy.ssadang.global.security.JwtAuthenticationEntryPoint;
import com.ssafy.ssadang.global.security.provider.TokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {
    @Bean
    public TokenProvider tokenProvider(JwtProperties jwtProperties) {
        return new TokenProvider(jwtProperties.getSecret(), jwtProperties.getAccessTokenValidityInSeconds(), jwtProperties.getRefreshTokenValidityInSeconds());
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public JwtAccessDeniedHandler jwtAccessDeniedHandler() {
        return new JwtAccessDeniedHandler();
    }

}
