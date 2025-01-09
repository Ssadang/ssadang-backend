package com.ssafy.ssadang.domain.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssadang.domain.user.dto.request.EmailAuthNumberRequestDto;
import com.ssafy.ssadang.domain.user.dto.request.EmailSendRequestDto;
import com.ssafy.ssadang.domain.user.dto.request.LoginRequestDto;
import com.ssafy.ssadang.domain.user.dto.request.SignupRequestDto;
import com.ssafy.ssadang.domain.user.service.UserService;
import com.ssafy.ssadang.global.common.ApiResponseJson;
import com.ssafy.ssadang.global.security.dto.response.AccessTokenInfoResponseDto;
import com.ssafy.ssadang.global.security.dto.response.TokenResponseDto;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService service;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody SignupRequestDto signupRequestDto) {
		return ResponseEntity.ok(service.signup(signupRequestDto));
	}

	@PostMapping("/sendmail")
	public ResponseEntity<?> sendmail(@RequestBody EmailSendRequestDto dto) {
		return ResponseEntity.ok(service.sendmail(dto));
	}

	@PostMapping("/mailcheck")
	public ResponseEntity<?> mailcheck(@RequestBody EmailAuthNumberRequestDto dto) {
		return ResponseEntity.ok(service.mailcheck(dto));
	}

	@PostMapping("/login")
	public ApiResponseJson login(@RequestBody LoginRequestDto dto, HttpServletResponse response) {

		TokenResponseDto tokenResponseDto = service.login(dto.getEmail(), dto.getPassword());

		// refresh 토큰은 쿠키에 저장
		Cookie cookie = new Cookie("refresh", tokenResponseDto.getRefreshTokenInfoResponse());
		// cookie 설정
		cookie.setMaxAge(604800);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);

		return new ApiResponseJson(HttpStatus.OK, tokenResponseDto.getAccessTokenInfoResponse());
	}

	@PostMapping("/reissue")
	public ApiResponseJson reissue(@CookieValue(value = "refresh") String refresh, HttpServletResponse response) {
		TokenResponseDto tokenResponseDto = service.reissue(refresh);

		// refresh 토큰은 쿠키에 저장
		Cookie cookie = new Cookie("refresh", tokenResponseDto.getRefreshTokenInfoResponse());
		// cookie 설정
		cookie.setMaxAge((int)tokenResponseDto.getRefreshExpireTime());
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		
		return new ApiResponseJson(HttpStatus.OK, tokenResponseDto.getAccessTokenInfoResponse());
	}

	@GetMapping("/test")
	public String test() {
		return "hello";
	}
}
