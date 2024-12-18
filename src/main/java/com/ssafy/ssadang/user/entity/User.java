package com.ssafy.ssadang.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_tb")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	private String email;
	private String password;
	private String name;
	private String nickname;
	private String profileImgUrl;
	private String proveImgUrl;
    @CreationTimestamp  // 엔티티가 처음 생성될 때 자동으로 현재 시간 설정
    private LocalDateTime createDate;
    @UpdateTimestamp  // 엔티티가 수정될 때마다 자동으로 현재 시간 설정
    private LocalDateTime updateDate;
	private int grade;
	private int areaId;
	private int shareCount;
}
