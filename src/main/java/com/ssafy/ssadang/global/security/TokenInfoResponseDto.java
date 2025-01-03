package com.ssafy.ssadang.global.security;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString(exclude= {"accessToken"})
public class TokenInfoResponseDto {
	private String accessToken; // access token
	
	private Date accessTokenExpireTime; // 토큰의 만료날
	private String email; // 사용자 이메일
	private String tokenId;
}
