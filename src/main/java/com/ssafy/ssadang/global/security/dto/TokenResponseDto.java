package com.ssafy.ssadang.global.security.dto;

import lombok.Data;

@Data
public class TokenResponseDto {
	private AccessTokenInfoResponseDto accessTokenInfoResponse;
	private RefreshTokenInfoResponseDto refreshTokenInfoResponse;
}
