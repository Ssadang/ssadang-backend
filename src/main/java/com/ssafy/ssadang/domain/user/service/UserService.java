package com.ssafy.ssadang.domain.user.service;

import com.ssafy.ssadang.domain.user.dto.SignupRequestDto;
import com.ssafy.ssadang.domain.user.entity.User;

public interface UserService {
	public int signup(SignupRequestDto dto);
	
	User findById(Integer id);
}
