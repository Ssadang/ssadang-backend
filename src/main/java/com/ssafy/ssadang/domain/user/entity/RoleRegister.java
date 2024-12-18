package com.ssafy.ssadang.domain.user.entity;

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
@Table(name = "role_register_tb")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleRegister {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleRegisterId;
	private int roleId;
	private int userId;
}
