package com.ssafy.ssadang.user.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ssafy.ssadang.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
	
	public User toUserEntity(BCryptPasswordEncoder passwordEncoder) {
		return User
				.builder()
				.email(email)
				.password(passwordEncoder.encode(password))
				.name(name)
				.nickname(nickname)
				.areaId(areaId)
				.profileImgUrl(profileImgUrl)
				.proveImgUrl(proveImgUrl)
				.grade(grade)
				.build();
	}
	
	
}
