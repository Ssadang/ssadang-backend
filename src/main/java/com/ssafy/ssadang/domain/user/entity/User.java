package com.ssafy.ssadang.domain.user.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user_tb")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;
	private String email;
	private String password;
	private String name;
	private String nickname;
	@Column(name = "profile_img_url")
	private String profileImgUrl;
	@Column(name = "prove_img_url")
	private String proveImgUrl;
	@CreationTimestamp // 엔티티가 처음 생성될 때 자동으로 현재 시간 설정
	@Column(name = "create_date")
	private LocalDateTime createDate;
	@UpdateTimestamp // 엔티티가 수정될 때마다 자동으로 현재 시간 설정
	@Column(name = "update_date")
	private LocalDateTime updateDate;
	private int grade;
	@Column(name = "area_id")
	private int areaId;
	@Column(name = "share_count")
	private int shareCount;

	@OneToMany(mappedBy = "user")
	private List<RoleRegister> roleRegister;
}
