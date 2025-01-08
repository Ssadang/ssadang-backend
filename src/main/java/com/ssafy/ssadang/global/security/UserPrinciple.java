package com.ssafy.ssadang.global.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class UserPrinciple extends User{
	
	private static final String PASSWORD_ERASED_VALUE = "{PASSWORD_ERASED}"; // Password를 인증할 때만 사용하고 지워버리는 용도이다.
	private final String email;

	public UserPrinciple(String email, String username, Collection<? extends GrantedAuthority> authorities) {
		super(username, PASSWORD_ERASED_VALUE, authorities);
		this.email = email;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "UserPrinciple("+
				"email= "+email +
				"username="+getUsername()+
				"role=" + getAuthorities();
	}
}
