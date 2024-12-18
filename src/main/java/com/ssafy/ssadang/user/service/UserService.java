package com.ssafy.ssadang.user.service;

import org.springframework.stereotype.Service;

import com.ssafy.ssadang.user.dto.SignupRequestDto;

public interface UserService {
	public int signup(SignupRequestDto dto);
}
