package com.ssafy.ssadang.domain.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user_tb")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User {
	
	@Id
	private Integer userId;
	private String email;
	private String password;
	private String name;
	private String nickname;
	private String profileImgUrl;
	private String proveImgUrl;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	private Integer grade;
	private Integer areaId;
	private Integer shareCount;

}
