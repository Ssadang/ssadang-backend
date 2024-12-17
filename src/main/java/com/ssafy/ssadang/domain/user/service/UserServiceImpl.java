package com.ssafy.ssadang.domain.user.service;

import org.springframework.stereotype.Service;

import com.ssafy.ssadang.domain.user.entity.User;
import com.ssafy.ssadang.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;

	@Override
	public User findById(Integer userId) {
		return userRepository.findById(userId).orElseThrow();
	}

}
