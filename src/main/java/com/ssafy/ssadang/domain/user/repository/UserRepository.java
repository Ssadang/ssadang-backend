package com.ssafy.ssadang.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssadang.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
