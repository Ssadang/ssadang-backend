package com.ssafy.ssadang.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.ssadang.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

	@Query("SELECT u FROM User u " +
		       "LEFT JOIN FETCH u.roleRegister rr " +
		       "LEFT JOIN FETCH rr.role r " +
		       "WHERE u.userId = :userId")
    User findUserWithRoleNameById(@Param("userId") int userId);
}
