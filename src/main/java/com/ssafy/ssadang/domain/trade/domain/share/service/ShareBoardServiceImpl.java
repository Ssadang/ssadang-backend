package com.ssafy.ssadang.domain.trade.domain.share.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ssadang.domain.trade.domain.share.dto.ShareBoardDetailResponseDto;
import com.ssafy.ssadang.domain.trade.domain.share.dto.ShareBoardDto;
import com.ssafy.ssadang.domain.trade.domain.share.dto.ShareBoardRequestDto;
import com.ssafy.ssadang.domain.trade.domain.share.entity.ShareBoard;
import com.ssafy.ssadang.domain.trade.domain.share.entity.ShareBoardStatusRelationship;
import com.ssafy.ssadang.domain.trade.domain.share.entity.ShareFavorite;
import com.ssafy.ssadang.domain.trade.domain.share.entity.ShareImage;
import com.ssafy.ssadang.domain.trade.domain.share.entity.ShareParticipant;
import com.ssafy.ssadang.domain.trade.domain.share.repository.ShareBoardRepository;
import com.ssafy.ssadang.domain.trade.domain.share.repository.ShareBoardSpecification;
import com.ssafy.ssadang.domain.trade.domain.share.repository.ShareBoardStatusRelationshipRepository;
import com.ssafy.ssadang.domain.trade.domain.share.repository.ShareFavoriteRepository;
import com.ssafy.ssadang.domain.trade.domain.share.repository.ShareImageRepository;
import com.ssafy.ssadang.domain.trade.domain.share.repository.ShareParticipantRepository;
import com.ssafy.ssadang.domain.user.dto.UserDto;
import com.ssafy.ssadang.domain.user.service.UserService;
import com.ssafy.ssadang.infra.aws.AmazonS3Uploader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ShareBoardServiceImpl implements ShareBoardService {
	
	private final int DEFAULT_PAGE_SIZE = 20;

	private final AmazonS3Uploader amazonS3Uploader;
	
	private final UserService userService;
	
	private final ShareBoardRepository shareBoardRepository;
	private final ShareImageRepository shareImageRepository;
	private final ShareFavoriteRepository shareFavoriteRepository;
	private final ShareBoardStatusRelationshipRepository shareBoardStatusRelationshipRepository;
	private final ShareParticipantRepository shareParticipantRepository;
	
	@Override
	public ShareBoardDetailResponseDto upload(Integer authorId, ShareBoardRequestDto shareBoardRequestDto) {
		UserDto authorDto = userService.findDtoById(authorId);
		ShareBoard shareBoard = ShareBoard.builder()
				.authorId(authorDto.getUserId())
				.createDate(LocalDateTime.now())
				.title(shareBoardRequestDto.getTitle())
				.description(shareBoardRequestDto.getDescription())
				.itemCategoryId(shareBoardRequestDto.getItemCategoryId())
				.areaId(shareBoardRequestDto.getAreaId())
				.gifticonId(shareBoardRequestDto.getGifticonId())
				.hitCount(0)
				.build();
		ShareBoard savedShareBoard = shareBoardRepository.save(shareBoard);
		saveImages(savedShareBoard.getShareBoardId(), shareBoardRequestDto.getImages());
		return toShareBoardDetailDto(savedShareBoard, authorDto);
	}
	
	@Override
	public List<ShareBoardDto> findAllDtoByAuthorId(Integer authorId, Integer loginUserId) {
		List<ShareBoard> sameAuthorShareBoards = shareBoardRepository
				.findAllByAuthorIdOrderByCreateDateDesc(authorId);
		return sameAuthorShareBoards.stream()
				.map(shareBoard -> toShareBoardDto(shareBoard, loginUserId)).toList();
	}

	@Override
	public List<ShareBoardDto> findAllDtoByItemCategoryId(Integer itemCategoryId, Integer loginUserId) {
		List<ShareBoard> similarShareBoards = shareBoardRepository
				.findAllByItemCategoryIdOrderByCreateDateDesc(itemCategoryId);
		return similarShareBoards.stream()
				.map(shareBoard -> toShareBoardDto(shareBoard, loginUserId)).toList();
	}
	
	@Override
	public ShareBoardDetailResponseDto view(Integer loginUserId, Integer shareBoardid) {
		ShareBoard shareBoard = shareBoardRepository.findById(shareBoardid).orElseThrow();
		shareBoard.increaseHitCount();
		return toShareBoardDetailDto(shareBoard, userService.findDtoById(loginUserId));
	}
	
	@Override
	public List<ShareBoardDto> list(Integer loginUserId, String keyword, Integer cursorId) {
		Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE, Sort.by("createDate").descending());
		Page<ShareBoard> page = null;
		if (keyword == null || keyword.isBlank()) {
			if (cursorId == null) {
				page = shareBoardRepository.findAll(pageable);
			} else {
				ShareBoard cursor = shareBoardRepository.findById(cursorId).orElseThrow();
				page = shareBoardRepository
						.findAllByCreateDateLessThan(cursor.getCreateDate(), pageable);
			}
		} else {
			String[] keywords = keyword.split(" ");
			Specification<ShareBoard> specification = null;
			if (cursorId == null) {
				specification = ShareBoardSpecification.hasAllKeywordsIn(keywords);
			} else {
				ShareBoard cursor = shareBoardRepository.findById(cursorId).orElseThrow();
				specification = ShareBoardSpecification
						.isCreateDateLessThanAndHasAllKeywordsIn(cursor.getCreateDate(), keywords);
			}
			page = shareBoardRepository.findAll(specification, pageable);
		}
		return page.stream().map(shareBoard -> toShareBoardDto(shareBoard, loginUserId)).toList();
	}
	
	private void saveImages(Integer shareBoardId, List<MultipartFile> images) {
		if (images == null || images.isEmpty()) {
			return;
		}
		List<ShareImage> shareImages = new ArrayList<>();
		for (MultipartFile image : images) {
			String path = amazonS3Uploader.uploadImage(image);
			ShareImage shareImage = ShareImage.builder()
					.shareBoardId(shareBoardId)
					.path(path)
					.build();
			shareImages.add(shareImage);
		}
		shareImageRepository.saveAll(shareImages);
	}
	
	private ShareBoardDto toShareBoardDto(ShareBoard shareBoard, Integer loginUserId) {
		Set<ShareFavorite> shareFavorites = shareFavoriteRepository
				.findAllByShareBoardId(shareBoard.getShareBoardId());
		boolean favorite = shareFavorites.stream()
				.anyMatch(shareFavorite -> shareFavorite.getUserId().equals(loginUserId));
		return new ShareBoardDto(
				shareBoard,
				userService.findDtoById(shareBoard.getAuthorId()),
				shareImageRepository.findAllByShareBoardId(shareBoard.getShareBoardId()).stream()
						.map(shareImage -> shareImage.getPath()).toList(),
				shareFavorites.size(),
				favorite);
	}
	
	private ShareBoardDetailResponseDto toShareBoardDetailDto(ShareBoard shareBoard, UserDto loginUserDto) {
		ShareBoardDto shareBoardDto = toShareBoardDto(shareBoard, loginUserDto.getUserId());
		List<ShareBoardDto> sameAuthorShareBoardDtos = findAllDtoByAuthorId(shareBoard.getAuthorId(),
				loginUserDto.getUserId()).stream()
				.filter(dto -> !dto.getShareBoardId().equals(shareBoard.getShareBoardId()))
				.toList();
		List<ShareBoardDto> similarShareBoardDtos = findAllDtoByItemCategoryId(shareBoard.getItemCategoryId(),
				loginUserDto.getUserId()).stream()
				.filter(dto -> !dto.getShareBoardId().equals(shareBoard.getShareBoardId()))
				.toList();
		return new ShareBoardDetailResponseDto(shareBoardDto, sameAuthorShareBoardDtos, similarShareBoardDtos);
	}
	
	@Override
	public void deleteById(Integer loginUserId, Integer shareBoardId) {
		ShareBoard shareBoard = shareBoardRepository.findById(shareBoardId).orElseThrow();
		addStatus(shareBoard, 1);
	}
	
	@Override
	public void setStatusById(Integer loginUserId, Integer shareBoardId, Map<String, Integer> status) {
		ShareBoard shareBoard = shareBoardRepository.findById(shareBoardId).orElseThrow();
		switch (status.get("status")) {
		case 2: // USED
			close(shareBoard);
			break;
		}
	}
	
	private void close(ShareBoard shareBoard) {
		if (hasStatus(shareBoard, 1)) {
			throw new IllegalArgumentException("Gifticon alreaady has been deleted");
		}
		addStatus(shareBoard, 2);
	}
	
	private boolean hasStatus(ShareBoard shareBoard, Integer boardStatusId) {
		return shareBoardStatusRelationshipRepository.findAllByShareBoardId(shareBoard.getGifticonId()).stream()
				.anyMatch(gifticonStatusRelationship -> gifticonStatusRelationship.getBoardStatusId()
						.equals(boardStatusId));
	}
	
	private void addStatus(ShareBoard shareBoard, Integer boardStatusId) {
		boolean statusPresent = hasStatus(shareBoard, boardStatusId);
		if (statusPresent) {
			throw new IllegalArgumentException("Gifticon already has the status: " + boardStatusId);
		}
		ShareBoardStatusRelationship shareBoardStatusRelationship = ShareBoardStatusRelationship.builder()
				.shareBoardId(shareBoard.getGifticonId())
				.boardStatusId(boardStatusId)
				.build();
		shareBoardStatusRelationshipRepository.save(shareBoardStatusRelationship);
	}
	
	@Override
	public void addFavorite(Integer loginUserId, Integer shareBoardId) {
		shareFavoriteRepository.save(ShareFavorite.builder()
				.shareBoardId(shareBoardId)
				.userId(loginUserId)
				.build());
	}
	
	@Override
	public void deleteFavorite(Integer loginUserId, Integer shareBoardId) {
		shareFavoriteRepository.deleteByShareBoardIdAndUserId(shareBoardId, loginUserId);
	}
	
	@Override
	public void participateGame(Integer loginuserId, Integer shareBoardId) {
		shareParticipantRepository.findByShareBoardIdAndUserId(shareBoardId, loginuserId)
				.ifPresent(shareParticipant -> {
					throw new IllegalArgumentException();
				});
		shareParticipantRepository.save(
				ShareParticipant.builder()
						.shareBoardId(shareBoardId)
						.userId(loginuserId)
						.build());
		Set<ShareParticipant> shareParticipants = shareParticipantRepository.findAllByShareBoardId(shareBoardId);
		int currentShareParticipantCount = shareParticipants.size();
		ShareBoard shareBoard = shareBoardRepository.findById(shareBoardId).orElseThrow();
		if (currentShareParticipantCount == shareBoard.getMaxParticipantCount()) {
			int winnerIdx = (int) (Math.random() * currentShareParticipantCount);
			Integer winnerId = shareParticipants.stream().skip(winnerIdx).findFirst().orElseThrow().getUserId();
			shareBoard.updateWinnerid(winnerId);
		}
	}
	
	@Override
	public void exitGame(Integer loginuserId, Integer shareBoardId) {
		shareParticipantRepository.deleteByShareBoardIdAndUserId(shareBoardId, loginuserId);
	}

}
