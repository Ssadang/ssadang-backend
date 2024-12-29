package com.ssafy.ssadang.domain.gifticon.service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ssadang.domain.gifticon.dto.GifticonRequestDto;
import com.ssafy.ssadang.domain.gifticon.dto.GifticonResponseDto;
import com.ssafy.ssadang.domain.gifticon.entity.Gifticon;
import com.ssafy.ssadang.domain.gifticon.entity.GifticonStatusRelationship;
import com.ssafy.ssadang.domain.gifticon.repository.GifticonRepository;
import com.ssafy.ssadang.domain.gifticon.repository.GifticonStatusRelationshipRepository;
import com.ssafy.ssadang.domain.user.service.UserService;
import com.ssafy.ssadang.infra.aws.AmazonS3Uploader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GifticonServiceImpl implements GifticonService {
	
	private final UserService userService;

	private final AmazonS3Uploader amazonS3Uploader;
	
	private final GifticonRepository gifticonRepository;
	private final GifticonStatusRelationshipRepository gifticonStatusRelationshipRepository;
	
	@Override
	public GifticonResponseDto save(GifticonRequestDto gifticonRequestDto) {
		// TODO owner 설정
		String imagePath = amazonS3Uploader.uploadImage(gifticonRequestDto.getImage());
		Gifticon gifticon = Gifticon.builder()
				.ownerId(gifticonRequestDto.getOwnerId())
				.imagePath(imagePath)
				.expiryDate(gifticonRequestDto.getExpiryDate())
				.name(gifticonRequestDto.getName())
				.build();
		Gifticon savedGifticon = gifticonRepository.save(gifticon);
		return GifticonResponseDto.fromEntity(savedGifticon);
	}

	@Override
	public GifticonResponseDto findById(Integer id) {
		Gifticon gifticon = gifticonRepository.findById(id).orElseThrow();
		if (hasStatus(gifticon, 1))  {
			throw new NoSuchElementException();
		}
		return GifticonResponseDto.fromEntity(gifticon);
	}

	@Override
	public List<GifticonResponseDto> findAllByOwnerId(Integer ownerId) {
		return gifticonRepository.findAllByOwnerIdOrderByExpiryDate(userService.findDtoById(ownerId).getUserId())
				.stream().filter(gifticon -> !hasStatus(gifticon, 1))
				.map(giftion -> GifticonResponseDto.fromEntity(giftion)).toList();
	}

	@Override
	public void deleteById(Integer id) {
		Gifticon gifticon = gifticonRepository.findById(id).orElseThrow();
		addStatus(gifticon, 1);
	}
	
	@Override
	public void setStatusById(Integer id, Map<String, Integer> status) {
		Gifticon gifticon = gifticonRepository.findById(id).orElseThrow();
		switch (status.get("status")) {
		case 2: // USED
			useById(gifticon);
			break;
		}
	}
	
	private void useById(Gifticon gifticon) {
		if (hasStatus(gifticon, 1)) {
			throw new IllegalArgumentException("Gifticon alreaady has been deleted");
		}
		addStatus(gifticon, 2);
	}
	
	private boolean hasStatus(Gifticon gifticon, Integer gifticonStatusId) {
		return gifticonStatusRelationshipRepository.findAllByGifticonId(gifticon.getGifticonId()).stream()
				.anyMatch(gifticonStatusRelationship -> gifticonStatusRelationship.getGifticonStatusId()
						.equals(gifticonStatusId));
	}
	
	private void addStatus(Gifticon gifticon, Integer gifticonStatusId) {
		boolean statusPresent = hasStatus(gifticon, gifticonStatusId);
		if (statusPresent) {
			throw new IllegalArgumentException("Gifticon already has the status: " + gifticonStatusId);
		}
		GifticonStatusRelationship gifticonStatusRelationship = GifticonStatusRelationship.builder()
				.gifticonId(gifticon.getGifticonId())
				.gifticonStatusId(gifticonStatusId)
				.build();
		gifticonStatusRelationshipRepository.save(gifticonStatusRelationship);
	}

}
