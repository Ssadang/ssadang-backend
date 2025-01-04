package com.ssafy.ssadang.global.security.dto.response;

import lombok.Data;

@Data
public class TokenResponseDto {
	private AccessTokenInfoResponseDto accessTokenInfoResponse;
	private String refreshTokenInfoResponse;
}
