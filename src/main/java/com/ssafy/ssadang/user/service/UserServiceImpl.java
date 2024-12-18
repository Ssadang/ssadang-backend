package com.ssafy.ssadang.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.ssadang.user.dto.SignupRequestDto;
import com.ssafy.ssadang.user.entity.User;
import com.ssafy.ssadang.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public int signup(SignupRequestDto dto) {
		// TODO Auto-generated method stub
		User user = dto.toUserEntity(passwordEncoder);
		User saveUser = repo.save(user);
		System.out.println(saveUser);
		if(saveUser != null) return 1;
		else return 0;
	}
}
