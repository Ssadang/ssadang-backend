package com.ssafy.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 인가 및 설정을 담당
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	// 비밀번호 암호화 메서드
	// 단방향 암호화
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// csrf disable
		http.csrf((auth) -> auth.disable());
		// form 로그인 방식 disable
		http.formLogin((auth) -> auth.disable());
		// http basic 인증 방식 disable
		http.httpBasic((auth) -> auth.disable());
		// 인가 정책
		http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/**").permitAll() // 로그인 안해도 인가를 얻을 수 있음
                .anyRequest().authenticated());// 로그인 해야 인가를 얻을 수 있음
		// 세션을 서버에서 관리 하지않겠다라는걸 명시
        http
        .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                
		return http.build();
	}
}
