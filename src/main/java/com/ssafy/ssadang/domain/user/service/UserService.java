package com.ssafy.ssadang.domain.user.service;

import com.ssafy.ssadang.domain.user.dto.EmailSendRequestDto;
import com.ssafy.ssadang.domain.user.dto.SignupRequestDto;
import com.ssafy.ssadang.domain.user.entity.User;

public interface UserService {
	public int signup(SignupRequestDto dto);
	public int sendmail(EmailSendRequestDto dto	);
	
	User findById(Integer id);
}
