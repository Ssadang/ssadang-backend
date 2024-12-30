package com.ssafy.ssadang.domain.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssadang.domain.user.dto.request.EmailAuthNumberRequestDto;
import com.ssafy.ssadang.domain.user.dto.request.EmailSendRequestDto;
import com.ssafy.ssadang.domain.user.dto.request.SignupRequestDto;
import com.ssafy.ssadang.domain.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService service;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@ModelAttribute SignupRequestDto signupRequestDto) {
		return ResponseEntity.ok(service.signup(signupRequestDto));
	}
	
	@PostMapping("/sendmail")
	public ResponseEntity<?> sendmail(@RequestBody EmailSendRequestDto dto){
		return ResponseEntity.ok(service.sendmail(dto));
	}
	
	@PostMapping("/mailcheck")
	public ResponseEntity<?> mailcheck(@RequestBody EmailAuthNumberRequestDto dto){
		return ResponseEntity.ok(service.mailcheck(dto));
	}
}
