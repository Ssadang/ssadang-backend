package com.ssafy.ssadang.domain.gifticon.service;

import org.springframework.stereotype.Service;

import com.ssafy.ssadang.domain.gifticon.dto.GifticonRequestDto;
import com.ssafy.ssadang.domain.gifticon.dto.GifticonResponseDto;
import com.ssafy.ssadang.domain.gifticon.entity.Gifticon;
import com.ssafy.ssadang.domain.gifticon.repository.GifticonRepository;
import com.ssafy.ssadang.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GifticonServiceImpl implements GifticonService {
	
	private final UserService userService;

	private final GifticonRepository gifticonRepository;
	
	@Override
	public GifticonResponseDto save(GifticonRequestDto gifticonRequestDto) {
		// TODO owner, imagePath 설정
		Gifticon gifticon = Gifticon.builder()
				.owner(userService.findById(gifticonRequestDto.getOwnerId()))
				.imagePath(gifticonRequestDto.getImage().getOriginalFilename())
				.expiryDate(gifticonRequestDto.getExpiryDate())
				.name(gifticonRequestDto.getName())
				.used(false)
				.build();
		Gifticon savedGifticon = gifticonRepository.save(gifticon);
		return GifticonResponseDto.fromEntity(savedGifticon);
	}

}
