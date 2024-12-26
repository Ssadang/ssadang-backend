package com.ssafy.ssadang.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.ssadang.domain.user.dto.EmailSendRequestDto;
import com.ssafy.ssadang.domain.user.dto.SignupRequestDto;
import com.ssafy.ssadang.domain.user.entity.RoleRegister;
import com.ssafy.ssadang.domain.user.entity.User;
import com.ssafy.ssadang.domain.user.repository.RoleRegisterRepository;
import com.ssafy.ssadang.domain.user.repository.UserRepository;
import com.ssafy.ssadang.global.util.RandomStringGenerator;
import com.ssafy.ssadang.infra.aws.AmazonS3Uploader;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRegisterRepository roleRegisterRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AmazonS3Uploader uploader;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public int signup(SignupRequestDto dto) {
		// TODO Auto-generated method stub
		User user = dto.toUserEntity();
		// password
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		
		// image upload
		if(dto.getProfileImg() != null) user.setProfileImgUrl(uploader.uploadImage(dto.getProfileImg()));
		if(dto.getProveImg() != null) user.setProveImgUrl(uploader.uploadImage(dto.getProveImg()));
		
		User saveUser = userRepo.save(user);
		
		// role regist
		RoleRegister roleRegister = new RoleRegister();
		roleRegister.setRoleId(0); // 0 번 임시사용자
		roleRegister.setUserId(saveUser.getUserId());
		RoleRegister saveRoleRegister = roleRegisterRepo.save(roleRegister);
		
		if(saveUser != null && saveRoleRegister != null) return 1;
		else return 0;
	}

	@Override
	public User findById(Integer id) {
		return userRepo.findById(id).orElseThrow();
	}

	    
	@Override
	public int sendmail(EmailSendRequestDto dto) {
		//redis에 저장
		String randomSix = RandomStringGenerator.generateRandomNumber(); // 6자리수 생성
		
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			messageHelper.setSubject("이메일 주소 확인");
			messageHelper.setTo(dto.getEmail());
			messageHelper.setText(randomSix);
			javaMailSender.send(mimeMessage);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
