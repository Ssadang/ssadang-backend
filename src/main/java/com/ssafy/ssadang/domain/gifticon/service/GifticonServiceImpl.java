package com.ssafy.ssadang.domain.gifticon.service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.security.access.AccessDeniedException;
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
	public GifticonResponseDto save(Integer ownerId, GifticonRequestDto gifticonRequestDto) {
		String imagePath = amazonS3Uploader.uploadImage(gifticonRequestDto.getImage());
		Gifticon gifticon = Gifticon.builder()
				.ownerId(ownerId)
				.imagePath(imagePath)
				.expiryDate(gifticonRequestDto.getExpiryDate())
				.name(gifticonRequestDto.getName())
				.build();
		Gifticon savedGifticon = gifticonRepository.save(gifticon);
		return GifticonResponseDto.fromEntity(savedGifticon);
	}

	@Override
	public GifticonResponseDto findById(Integer ownerId, Integer gifticonId) {
		Gifticon gifticon = gifticonRepository.findById(gifticonId).orElseThrow();
		if (!ownerId.equals(gifticon.getOwnerId())) {
			throw new AccessDeniedException("Access denied");
		}
		if (hasStatus(gifticon, 1))  {
			throw new NoSuchElementException();
		}
		return GifticonResponseDto.fromEntity(gifticon);
	}

	@Override
	public List<GifticonResponseDto> findByOwnerId(Integer ownerId) {
		return gifticonRepository.findAllByOwnerIdOrderByExpiryDate(userService.findDtoById(ownerId).getUserId())
				.stream().filter(gifticon -> !hasStatus(gifticon, 1))
				.map(giftion -> GifticonResponseDto.fromEntity(giftion)).toList();
	}

	@Override
	public void deleteById(Integer ownerId, Integer gifticonId) {
		Gifticon gifticon = gifticonRepository.findById(gifticonId).orElseThrow();
		if (!ownerId.equals(gifticon.getOwnerId())) {
			throw new AccessDeniedException("Access denied");
		}
		addStatus(gifticon, 1);
	}
	
	@Override
	public void setStatusById(Integer ownerId, Integer gifticonId, Map<String, Integer> status) {
		Gifticon gifticon = gifticonRepository.findById(gifticonId).orElseThrow();
		if (!ownerId.equals(gifticon.getOwnerId())) {
			throw new AccessDeniedException("Access denied");
		}
		switch (status.get("status")) {
		case 2: // USED
			use(gifticon);
			break;
		}
	}
	
	private void use(Gifticon gifticon) {
		if (hasStatus(gifticon, 1)) {
			throw new NoSuchElementException();
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
