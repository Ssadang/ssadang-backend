package com.ssafy.ssadang.domain.user.service;

import org.springframework.stereotype.Service;

import com.ssafy.ssadang.domain.user.dto.SignupRequestDto;

public interface UserService {
	public int signup(SignupRequestDto dto);
}
