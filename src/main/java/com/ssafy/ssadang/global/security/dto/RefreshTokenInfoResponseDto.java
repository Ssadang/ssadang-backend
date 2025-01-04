package com.ssafy.ssadang.global.security.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString(exclude= {"refreshToken"})
public class RefreshTokenInfoResponseDto {
	private String refreshToken; // refresh 토큰
	
	private Date refreshTokenExpireTime; // 토큰의 만료날
	private String email; // 사용자 이메일
	private String tokenId;
}
