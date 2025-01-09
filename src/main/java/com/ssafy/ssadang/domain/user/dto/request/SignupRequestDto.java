package com.ssafy.ssadang.domain.user.dto.request;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ssadang.domain.user.entity.User;

import lombok.Data;


@Data
public class SignupRequestDto {
	private String email;
	private String password;
	private String name;
	private String nickname;
	private int areaId;
	private String profileImgUrl;
	private String proveImgUrl;
	private int grade;
	
	public User toUserEntity() {
		return User
				.builder()
				.email(email)
				.name(name)
				.nickname(nickname)
				.profileImgUrl(profileImgUrl)
				.proveImgUrl(proveImgUrl)
				.areaId(areaId)
				.grade(grade)
				.build();
	}
	
	
}
