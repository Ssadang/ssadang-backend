package com.ssafy.ssadang.domain.user.service;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.ssadang.domain.user.dto.request.EmailAuthNumberRequestDto;
import com.ssafy.ssadang.domain.user.dto.request.EmailSendRequestDto;
import com.ssafy.ssadang.domain.user.dto.request.SignupRequestDto;
import com.ssafy.ssadang.domain.user.entity.Role;
import com.ssafy.ssadang.domain.user.entity.RoleRegister;
import com.ssafy.ssadang.domain.user.entity.User;
import com.ssafy.ssadang.domain.user.repository.RoleRegisterRepository;
import com.ssafy.ssadang.domain.user.repository.RoleRepository;
import com.ssafy.ssadang.domain.user.repository.UserRepository;
import com.ssafy.ssadang.global.security.dto.response.AccessTokenInfoResponseDto;
import com.ssafy.ssadang.global.security.dto.response.TokenResponseDto;
import com.ssafy.ssadang.global.security.provider.TokenProvider;
import com.ssafy.ssadang.global.util.RandomStringGenerator;
import com.ssafy.ssadang.global.util.RedisUtils;
import com.ssafy.ssadang.infra.aws.AmazonS3Uploader;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final long LIMIT_TIME = 180000; // mail 인증 만료시간

	@Value("${jwt.secret}")
	private String secret;
	private final TokenProvider tokenProvider;
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRegisterRepository roleRegisterRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private AmazonS3Uploader uploader;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private RedisUtils redisUtils;

	@Override
	public int signup(SignupRequestDto dto) {
		// TODO Auto-generated method stub
		User user = dto.toUserEntity();
		// password
		user.setPassword(passwordEncoder.encode(dto.getPassword()));

		// image upload
		if (dto.getProfileImg() != null)
			user.setProfileImgUrl(uploader.uploadImage(dto.getProfileImg()));
		if (dto.getProveImg() != null)
			user.setProveImgUrl(uploader.uploadImage(dto.getProveImg()));

		User saveUser = userRepo.save(user);

		Role role = roleRepo.findByRoleId(1); // 1 번 임시사용자.

		// role regist
		RoleRegister roleRegister = new RoleRegister(); // 1 번 임시사용자
		roleRegister.setUser(saveUser);
		roleRegister.setRole(role);
		RoleRegister saveRoleRegister = roleRegisterRepo.save(roleRegister);

		if (saveUser != null && saveRoleRegister != null)
			return 1;
		else
			return 0;
	}

	@Override
	public int sendmail(EmailSendRequestDto dto) {
		// redis에 저장
		if (redisUtils.getData(dto.getEmail()) != null)
			redisUtils.deleteData(dto.getEmail());
		;
		String authNumber = RandomStringGenerator.generateRandomNumber(); // 6자리수 생성
		redisUtils.setData(dto.getEmail(), authNumber, LIMIT_TIME);

		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			messageHelper.setSubject("이메일 주소 확인");
			messageHelper.setTo(dto.getEmail());
			messageHelper.setText(authNumber);
			javaMailSender.send(mimeMessage);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int mailcheck(EmailAuthNumberRequestDto dto) {
		String authNumber = redisUtils.getData(dto.getEmail());
		if (authNumber.equals(dto.getAuthNumber()))
			return 1;
		else
			return 0;
	}

	@Override
	public User findById(Integer id) {
		return userRepo.findById(id).orElseThrow();
	}

	@Override
	public User findUserWithRoleNameById(int userId) {
		return userRepo.findUserWithRoleNameById(userId);
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email).orElseThrow(() -> {
			log.info("계정이 존재하지 않음");
			return new IllegalArgumentException("계정이 존재하지 않습니다.");
		});
	}

	private void checkPassword(String password, User user) {
		if (!passwordEncoder.matches(password, user.getPassword())) {
			log.info("일치하지 않는 비밀번호");
			throw new BadCredentialsException("기존 비밀번호 확인에 실패했습니다.");
		}
	}

	@Override
	public TokenResponseDto login(String email, String password) {
		try {
			// user를 찾아야댐
			User user = findByEmail(email);
			User detailUser = findUserWithRoleNameById(user.getUserId());
			checkPassword(password, detailUser);
			
			AccessTokenInfoResponseDto accessTokenInfoResponseDto = tokenProvider.createAccessToken(detailUser);
			
			TokenResponseDto tokenResponseDto = new TokenResponseDto();
			tokenResponseDto.setAccessTokenInfoResponse(accessTokenInfoResponseDto);
			tokenResponseDto.setRefreshTokenInfoResponse(tokenProvider.createRefreshToken(detailUser, 604800 * 1000));
			
			// refresh 토큰을 redis에 저장
			redisUtils.setData(tokenResponseDto.getRefreshTokenInfoResponse(), email, (long)604800 * 1000);
			return tokenResponseDto;
			
			//refresh 토큰과 access token 두개를 발급한다.
		
		} catch (IllegalArgumentException | BadCredentialsException e) {
			throw new IllegalArgumentException("계정이 존재하지 않거나 비밀번호가 잘못되었습니다.");
		}

	}

	@Override
	public TokenResponseDto reissue(String refreshToken) {
		// redis에 refresh 토큰이 존재 하지 않는다면 그냥 검증할 수 없음
		// 위조, 만료, 전부다 막힘
		if(redisUtils.getData(refreshToken) == null) {
			throw new IllegalArgumentException("refresh 토큰이 유효하지 않습니다.");
		}else {
			// 토큰 파싱
			byte[] keyBytes = Decoders.BASE64.decode(secret);
			Key hashKey = Keys.hmacShaKeyFor(keyBytes);
			Claims claims = Jwts.parserBuilder().setSigningKey(hashKey).build().parseClaimsJws(refreshToken).getBody();
			String email = claims.getSubject();
			User user = findByEmail(email);
			User detailUser = findUserWithRoleNameById(user.getUserId());
			
			// refresh rotate
			// 토큰 재발급
			
			// access token 재발급
			AccessTokenInfoResponseDto accessTokenInfoResponseDto = tokenProvider.createAccessToken(detailUser);
			
			// refreshToken의 남은 시간(초)를 계산
			Date expirationDate = claims.getExpiration();
			long currentTimeMillis = System.currentTimeMillis();
			long remainingTimeMillis = expirationDate.getTime() - currentTimeMillis;
			long remainingSeconds = remainingTimeMillis / 1000;
			
			// refresh token 발급
			TokenResponseDto tokenResponseDto = new TokenResponseDto();
			tokenResponseDto.setRefreshExpireTime(remainingSeconds);
			tokenResponseDto.setAccessTokenInfoResponse(accessTokenInfoResponseDto);
			tokenResponseDto.setRefreshTokenInfoResponse(tokenProvider.createRefreshToken(detailUser, remainingSeconds * 1000));
			
			
			// 기존의 refresh token을 삭제
			redisUtils.deleteData(refreshToken);
			redisUtils.setData(tokenResponseDto.getRefreshTokenInfoResponse(), email, remainingSeconds * 1000);
			return tokenResponseDto;
		}
	}
}
