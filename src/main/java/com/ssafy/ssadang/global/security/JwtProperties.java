package com.ssafy.ssadang.global.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

// application.yml에 있는걸 가져오기 위함
@Data
@ConfigurationProperties(prefix = "jwt") // 알아서 jwt에 있는것과 매핑시켜줌
public class JwtProperties {
    private String header;
    private String secret;
    private Long accessTokenValidityInSeconds;

}
