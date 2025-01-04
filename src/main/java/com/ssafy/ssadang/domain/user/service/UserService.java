package com.ssafy.ssadang.domain.user.service;

import com.ssafy.ssadang.domain.user.dto.request.EmailAuthNumberRequestDto;
import com.ssafy.ssadang.domain.user.dto.request.EmailSendRequestDto;
import com.ssafy.ssadang.domain.user.dto.request.SignupRequestDto;
import com.ssafy.ssadang.domain.user.entity.User;
import com.ssafy.ssadang.global.security.dto.TokenResponseDto;

public interface UserService {
	public int signup(SignupRequestDto dto);
	public int sendmail(EmailSendRequestDto dto	);
	public int mailcheck(EmailAuthNumberRequestDto dto);
	public User findUserWithRoleNameById(int userId);
	public TokenResponseDto login(String email, String password);
	
	User findById(Integer id);
	User findByEmail(String email);
}
