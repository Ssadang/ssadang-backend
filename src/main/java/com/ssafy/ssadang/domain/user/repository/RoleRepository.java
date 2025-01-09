package com.ssafy.ssadang.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssadang.domain.user.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	Role findByRoleId(int roleId);
}
