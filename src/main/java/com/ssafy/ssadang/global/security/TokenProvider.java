package com.ssafy.ssadang.global.security;


import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ssafy.ssadang.domain.user.entity.RoleRegister;
import com.ssafy.ssadang.domain.user.entity.User;
import com.ssafy.ssadang.domain.user.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenProvider {
	
	private static final String AUTHORITIES_KEY = "auth"; // payload에 auth : role 이런씩
	private static final String TOKEN_ID_KEY = "tokenId"; // payload에 tokenId : 해쉬값
	private static final String USERNAME_KEY = "username"; // payload에 username : user 이름
	
	private final Key hashKey; // 서명 알고리즘에 쓰이는 key 값
	private final long accessTokenValidationInMilliseconds; // token 유효기간d
	
	public TokenProvider(String secrete, long accessTokenValidationInMilliseconds) {
		byte[] keyBytes = Decoders.BASE64.decode(secrete); // base64로 인코딩된 secrete key를 decoder로 decoding 하고 keyBytes에 저장후
		this.hashKey = Keys.hmacShaKeyFor(keyBytes);// 이걸 이용하려면 Key 객체로 wrapping 해야한다.
		this.accessTokenValidationInMilliseconds = accessTokenValidationInMilliseconds * 1000;
	}
	
	// 토큰 발급 로직
	// 로그인시
	public TokenInfoResponseDto createToken(User user) {
		long currentTime = new Date().getTime(); // 현재 시간
		Date accessTokenExpireTime = new Date(currentTime + accessTokenValidationInMilliseconds); // 현재 시간 + 만료기간
		String tokenId = UUID.randomUUID().toString(); // tokenId 발급
		
		// fetch role name logic
		List<RoleRegister> roleRegisterList = user.getRoleRegister();
		String role = null;
		
	
		for(int i = 0;i<roleRegisterList.size();i++) {
			if(i == 0) role = roleRegisterList.get(i).getRole().getName();
			else {
				String tmp = roleRegisterList.get(i).getRole().getName();
				role = role + "," + tmp;
			}
		}
		// fetch role name logic
		
		// jwt 만들기
		String accessToken = Jwts.builder()
				.setSubject(user.getEmail())
				.claim(AUTHORITIES_KEY, role)
				.claim(USERNAME_KEY, user.getName())
				.claim(TOKEN_ID_KEY, tokenId)
				.signWith(hashKey, SignatureAlgorithm.HS512) // 여기서 header가 결정돼기 떄문에 header를 설정안해줘도 됨
				.setExpiration(accessTokenExpireTime)
				.compact();
		
		return TokenInfoResponseDto.builder()
				.accessToken(accessToken)
				.email(user.getEmail())
				.accessTokenExpireTime(accessTokenExpireTime)
				.tokenId(tokenId)
				.build();
	}
	
	public TokenValidationResult validationToken(String token) {
		try {
			// token을 파싱해서 넘김
			// 토큰의 유효시간을 넘어버리면 exception
			Claims claims = Jwts.parserBuilder().setSigningKey(hashKey).build().parseClaimsJws(token).getBody();
			// Jwts.parserBuilder().setSigningKey(hashKey) 이렇게 해서 paser객체를 생성해준다.
			// 그리고 parseClaimsJws(token) 를 signature를 검증 후 jws(json web signature)로 만들어준다.
			// 그런다음 .getBody로 claim 부분을 가져오는 거임
			// .parseClaimsJws(token) 에서 검증이 안되면 아래 예외객체를 throws 한다.
			return new TokenValidationResult(TokenStatus.TOKEN_VALID, TokenType.ACCESS
					, claims.get(TOKEN_ID_KEY, String.class), claims);
		}
		catch(ExpiredJwtException e) {
			log.info("만료된 JWT 토큰");
			return getExpiredTokenValidationResult(e);
		}
		catch(SecurityException | MalformedJwtException e) {
			log.info("잘못된 JWT 서명");
			return new TokenValidationResult(TokenStatus.TOKEN_WRONG_SIGNATURE, null, null, null);
		}
		catch(UnsupportedJwtException e) {
			log.info("지원되지 않는 JWT 서명");
			return new TokenValidationResult(TokenStatus.TOKEN_HASH_NOT_SUPPORTED, null, null, null);
		}
		catch(IllegalArgumentException e) {
			log.info("잘못된 JWT 토큰");
			return new TokenValidationResult(TokenStatus.TOKEN_WRONG_SIGNATURE, null, null, null);
		}
	}
	
	// Security Context에 UsernamePasswordAuthenticationToken을 보내줌
	// 나중에 다른 filter에서 쓰임
	public Authentication getAuthentication(String token, Claims claims) {
		Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
		.map(SimpleGrantedAuthority::new)
		.collect(Collectors.toList());
		
		UserPrinciple principle = new UserPrinciple(claims.getSubject(), claims.get(USERNAME_KEY, String.class), authorities);
		
		return new UsernamePasswordAuthenticationToken(principle, token, authorities);
	}
	
	private TokenValidationResult getExpiredTokenValidationResult(ExpiredJwtException e) {
		Claims claims = e.getClaims();
		return new TokenValidationResult(TokenStatus.TOKEN_EXPIRED, TokenType.ACCESS, claims.get(TOKEN_ID_KEY, String.class), null);
	}
}
