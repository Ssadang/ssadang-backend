package com.ssafy.ssadang.domain.user.service;

import com.ssafy.ssadang.domain.user.dto.UserDto;
import com.ssafy.ssadang.domain.user.dto.request.EmailAuthNumberRequestDto;
import com.ssafy.ssadang.domain.user.dto.request.EmailSendRequestDto;
import com.ssafy.ssadang.domain.user.dto.request.SignupRequestDto;

public interface UserService {
	public int signup(SignupRequestDto dto);
	public int sendmail(EmailSendRequestDto dto	);
	public int mailcheck(EmailAuthNumberRequestDto dto);
	
	UserDto findDtoById(Integer id);
	UserDto findDtoByEmail(String email);
}
