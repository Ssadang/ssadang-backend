package com.ssafy.ssadang.domain.gifticon.service;

import org.springframework.stereotype.Service;

import com.ssafy.ssadang.domain.gifticon.dto.GifticonRequestDto;
import com.ssafy.ssadang.domain.gifticon.dto.GifticonResponseDto;
import com.ssafy.ssadang.domain.gifticon.entity.Gifticon;
import com.ssafy.ssadang.domain.gifticon.repository.GifticonRepository;
import com.ssafy.ssadang.domain.user.service.UserService;
import com.ssafy.ssadang.infra.aws.AmazonS3Uploader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GifticonServiceImpl implements GifticonService {
	
	private final UserService userService;

	private final AmazonS3Uploader amazonS3Uploader;
	
	private final GifticonRepository gifticonRepository;
	
	@Override
	public GifticonResponseDto save(GifticonRequestDto gifticonRequestDto) {
		// TODO owner 설정
		String imagePath = amazonS3Uploader.uploadImage(gifticonRequestDto.getImage());
		Gifticon gifticon = Gifticon.builder()
				.owner(userService.findById(gifticonRequestDto.getOwnerId()))
				.imagePath(imagePath)
				.expiryDate(gifticonRequestDto.getExpiryDate())
				.name(gifticonRequestDto.getName())
				.used(false)
				.build();
		Gifticon savedGifticon = gifticonRepository.save(gifticon);
		return GifticonResponseDto.fromEntity(savedGifticon);
	}

}
