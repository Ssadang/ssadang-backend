package com.ssafy.ssadang.domain.gifticon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssafy.ssadang.domain.gifticon.dto.GifticonRequestDto;
import com.ssafy.ssadang.domain.gifticon.dto.GifticonResponseDto;
import com.ssafy.ssadang.domain.gifticon.entity.Gifticon;
import com.ssafy.ssadang.domain.gifticon.entity.GifticonStatus;
import com.ssafy.ssadang.domain.gifticon.entity.GifticonStatusRelationship;
import com.ssafy.ssadang.domain.gifticon.repository.GifticonRepository;
import com.ssafy.ssadang.domain.gifticon.repository.GifticonStatusRelationshipRepository;
import com.ssafy.ssadang.domain.gifticon.repository.GifticonStatusRepository;
import com.ssafy.ssadang.domain.user.service.UserService;
import com.ssafy.ssadang.infra.aws.AmazonS3Uploader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GifticonServiceImpl implements GifticonService {
	
	private final UserService userService;

	private final AmazonS3Uploader amazonS3Uploader;
	
	private final GifticonRepository gifticonRepository;
	private final GifticonStatusRelationshipRepository gifticonStatusRelationshipRepository;
	private final GifticonStatusRepository gifticonStatusRepository;
	
	@Override
	public GifticonResponseDto save(GifticonRequestDto gifticonRequestDto) {
		// TODO owner 설정
		String imagePath = amazonS3Uploader.uploadImage(gifticonRequestDto.getImage());
		Gifticon gifticon = Gifticon.builder()
				.owner(userService.findById(gifticonRequestDto.getOwnerId()))
				.imagePath(imagePath)
				.expiryDate(gifticonRequestDto.getExpiryDate())
				.name(gifticonRequestDto.getName())
				.build();
		Gifticon savedGifticon = gifticonRepository.save(gifticon);
		return GifticonResponseDto.fromEntity(savedGifticon);
	}

	@Override
	public GifticonResponseDto findById(Integer id) {
		return GifticonResponseDto.fromEntity(gifticonRepository.findById(id).orElseThrow());
	}

	@Override
	public List<GifticonResponseDto> findAllByOwnerId(Integer ownerId) {
		return gifticonRepository.findAllByOwner(userService.findById(ownerId))
				.stream().map(giftion -> GifticonResponseDto.fromEntity(giftion)).toList();
	}

	@Override
	public void deleteById(Integer id) {
		addStatus(id, 1);
	}
	
	private void addStatus(Integer gifticonId, Integer gifticonStatusId) {
		Gifticon gifticon = gifticonRepository.findById(gifticonId).orElseThrow();
		GifticonStatus gifticonStatus = gifticonStatusRepository.findById(gifticonStatusId).orElseThrow();
		GifticonStatusRelationship gifticonStatusRelationship = GifticonStatusRelationship.builder()
				.gifticon(gifticon)
				.gifticonStatus(gifticonStatus)
				.build();
		gifticonStatusRelationshipRepository.save(gifticonStatusRelationship);
	}

}
