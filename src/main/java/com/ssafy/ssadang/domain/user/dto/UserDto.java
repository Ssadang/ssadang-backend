package com.ssafy.ssadang.domain.user.dto;

import com.ssafy.ssadang.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {

	private Integer userId;
	private String email;
	private String name;
	private String nickname;
	private String profileImgUrl;
	private String proveImgUrl;
	private int grade;
	private int areaId;
	
	public static UserDto fromEntity(User user) {
		return UserDto.builder()
				.userId(user.getUserId())
				.email(user.getEmail())
				.name(user.getName())
				.nickname(user.getNickname())
				.profileImgUrl(user.getProfileImgUrl())
				.proveImgUrl(user.getProveImgUrl())
				.grade(user.getGrade())
				.areaId(user.getAreaId())
				.build();
	}

}
