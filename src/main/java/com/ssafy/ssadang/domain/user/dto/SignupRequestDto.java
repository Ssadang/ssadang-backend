package com.ssafy.ssadang.domain.user.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ssadang.domain.user.entity.User;
import com.ssafy.ssadang.infra.aws.AmazonS3Uploader;

import lombok.Data;


@Data
public class SignupRequestDto {
	private String email;
	private String password;
	private String name;
	private String nickname;
	private int areaId;
	private MultipartFile profileImg;
	private MultipartFile proveImg;
	private int grade;
	
	public User toUserEntity() {
		return User
				.builder()
				.email(email)
				.name(name)
				.nickname(nickname)
				.areaId(areaId)
				.grade(grade)
				.build();
	}
	
	
}
