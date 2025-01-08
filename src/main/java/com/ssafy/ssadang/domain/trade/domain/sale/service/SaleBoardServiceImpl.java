package com.ssafy.ssadang.domain.trade.domain.sale.service;

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

import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardDetailResponseDto;
import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardDto;
import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardRequestDto;
import com.ssafy.ssadang.domain.trade.domain.sale.entity.SaleBoard;
import com.ssafy.ssadang.domain.trade.domain.sale.entity.SaleBoardStatusRelationship;
import com.ssafy.ssadang.domain.trade.domain.sale.entity.SaleFavorite;
import com.ssafy.ssadang.domain.trade.domain.sale.entity.SaleImage;
import com.ssafy.ssadang.domain.trade.domain.sale.repository.SaleBoardRepository;
import com.ssafy.ssadang.domain.trade.domain.sale.repository.SaleBoardSpecification;
import com.ssafy.ssadang.domain.trade.domain.sale.repository.SaleBoardStatusRelationshipRepository;
import com.ssafy.ssadang.domain.trade.domain.sale.repository.SaleFavoriteRepository;
import com.ssafy.ssadang.domain.trade.domain.sale.repository.SaleImageRepository;
import com.ssafy.ssadang.domain.user.dto.UserDto;
import com.ssafy.ssadang.domain.user.service.UserService;
import com.ssafy.ssadang.infra.aws.AmazonS3Uploader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class SaleBoardServiceImpl implements SaleBoardService {
	
	private final int DEFAULT_PAGE_SIZE = 20;

	private final AmazonS3Uploader amazonS3Uploader;
	
	private final UserService userService;
	
	private final SaleBoardRepository saleBoardRepository;
	private final SaleImageRepository saleImageRepository;
	private final SaleFavoriteRepository saleFavoriteRepository;
	private final SaleBoardStatusRelationshipRepository saleBoardStatusRelationshipRepository;
	
	@Override
	public SaleBoardDetailResponseDto upload(Integer authorId, SaleBoardRequestDto saleBoardRequestDto) {
		UserDto authorDto = userService.findDtoById(authorId);
		SaleBoard saleBoard = SaleBoard.builder()
				.authorId(authorDto.getUserId())
				.createDate(LocalDateTime.now())
				.title(saleBoardRequestDto.getTitle())
				.description(saleBoardRequestDto.getDescription())
				.price(saleBoardRequestDto.getPrice())
				.itemCategoryId(saleBoardRequestDto.getItemCategoryId())
				.areaId(saleBoardRequestDto.getAreaId())
				.gifticonId(saleBoardRequestDto.getGifticonId())
				.hitCount(0)
				.build();
		SaleBoard savedSaleBoard = saleBoardRepository.save(saleBoard);
		saveImages(savedSaleBoard.getSaleBoardId(), saleBoardRequestDto.getImages());
		return toSaleBoardDetailDto(savedSaleBoard, authorDto);
	}
	
	@Override
	public List<SaleBoardDto> findAllDtoByAuthorId(Integer authorId, Integer loginUserId) {
		List<SaleBoard> sameAuthorSaleBoards = saleBoardRepository
				.findAllByAuthorIdOrderByCreateDateDesc(authorId);
		return sameAuthorSaleBoards.stream()
				.map(saleBoard -> toSaleBoardDto(saleBoard, loginUserId)).toList();
	}

	@Override
	public List<SaleBoardDto> findAllDtoByItemCategoryId(Integer itemCategoryId, Integer loginUserId) {
		List<SaleBoard> similarSaleBoards = saleBoardRepository
				.findAllByItemCategoryIdOrderByCreateDateDesc(itemCategoryId);
		return similarSaleBoards.stream()
				.map(saleBoard -> toSaleBoardDto(saleBoard, loginUserId)).toList();
	}
	
	@Override
	public SaleBoardDetailResponseDto view(Integer loginUserId, Integer saleBoardid) {
		SaleBoard saleBoard = saleBoardRepository.findById(saleBoardid).orElseThrow();
		saleBoard.increaseHitCount();
		return toSaleBoardDetailDto(saleBoard, userService.findDtoById(loginUserId));
	}
	
	@Override
	public List<SaleBoardDto> list(Integer loginUserId, String keyword, Integer cursorId) {
		Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE, Sort.by("createDate").descending());
		Page<SaleBoard> page = null;
		if (keyword == null || keyword.isBlank()) {
			if (cursorId == null) {
				page = saleBoardRepository.findAll(pageable);
			} else {
				SaleBoard cursor = saleBoardRepository.findById(cursorId).orElseThrow();
				page = saleBoardRepository
						.findAllByCreateDateLessThan(cursor.getCreateDate(), pageable);
			}
		} else {
			String[] keywords = keyword.split(" ");
			Specification<SaleBoard> specification = null;
			if (cursorId == null) {
				specification = SaleBoardSpecification.hasAllKeywordsIn(keywords);
			} else {
				SaleBoard cursor = saleBoardRepository.findById(cursorId).orElseThrow();
				specification = SaleBoardSpecification
						.isCreateDateLessThanAndHasAllKeywordsIn(cursor.getCreateDate(), keywords);
			}
			page = saleBoardRepository.findAll(specification, pageable);
		}
		return page.stream().map(saleBoard -> toSaleBoardDto(saleBoard, loginUserId)).toList();
	}
	
	private void saveImages(Integer saleBoardId, List<MultipartFile> images) {
		if (images == null || images.isEmpty()) {
			return;
		}
		List<SaleImage> saleImages = new ArrayList<>();
		for (MultipartFile image : images) {
			String path = amazonS3Uploader.uploadImage(image);
			SaleImage saleImage = SaleImage.builder()
					.saleBoardId(saleBoardId)
					.path(path)
					.build();
			saleImages.add(saleImage);
		}
		saleImageRepository.saveAll(saleImages);
	}
	
	private SaleBoardDto toSaleBoardDto(SaleBoard saleBoard, Integer loginUserId) {
		Set<SaleFavorite> saleFavorites = saleFavoriteRepository
				.findAllBySaleBoardId(saleBoard.getSaleBoardId());
		boolean favorite = saleFavorites.stream()
				.anyMatch(saleFavorite -> saleFavorite.getUserId().equals(loginUserId));
		return new SaleBoardDto(
				saleBoard,
				userService.findDtoById(saleBoard.getAuthorId()),
				saleImageRepository.findAllBySaleBoardId(saleBoard.getSaleBoardId()).stream()
						.map(saleImage -> saleImage.getPath()).toList(),
				saleFavorites.size(),
				favorite);
	}
	
	private SaleBoardDetailResponseDto toSaleBoardDetailDto(SaleBoard saleBoard, UserDto loginUserDto) {
		SaleBoardDto saleBoardDto = toSaleBoardDto(saleBoard, loginUserDto.getUserId());
		List<SaleBoardDto> sameAuthorSaleBoardDtos = findAllDtoByAuthorId(saleBoard.getAuthorId(),
				loginUserDto.getUserId()).stream()
				.filter(dto -> !dto.getSaleBoardId().equals(saleBoard.getSaleBoardId()))
				.toList();
		List<SaleBoardDto> similarSaleBoardDtos = findAllDtoByItemCategoryId(saleBoard.getItemCategoryId(),
				loginUserDto.getUserId()).stream()
				.filter(dto -> !dto.getSaleBoardId().equals(saleBoard.getSaleBoardId()))
				.toList();
		return new SaleBoardDetailResponseDto(saleBoardDto, sameAuthorSaleBoardDtos, similarSaleBoardDtos);
	}
	
	@Override
	public void deleteById(Integer loginUserId, Integer saleBoardId) {
		SaleBoard saleBoard = saleBoardRepository.findById(saleBoardId).orElseThrow();
		addStatus(saleBoard, 1);
	}
	
	@Override
	public void setStatusById(Integer loginUserId, Integer saleBoardId, Map<String, Integer> status) {
		SaleBoard saleBoard = saleBoardRepository.findById(saleBoardId).orElseThrow();
		switch (status.get("status")) {
		case 2: // USED
			close(saleBoard);
			break;
		}
	}
	
	private void close(SaleBoard saleBoard) {
		if (hasStatus(saleBoard, 1)) {
			throw new IllegalArgumentException("Gifticon alreaady has been deleted");
		}
		addStatus(saleBoard, 2);
	}
	
	private boolean hasStatus(SaleBoard saleBoard, Integer boardStatusId) {
		return saleBoardStatusRelationshipRepository.findAllBySaleBoardId(saleBoard.getGifticonId()).stream()
				.anyMatch(gifticonStatusRelationship -> gifticonStatusRelationship.getBoardStatusId()
						.equals(boardStatusId));
	}
	
	private void addStatus(SaleBoard saleBoard, Integer boardStatusId) {
		boolean statusPresent = hasStatus(saleBoard, boardStatusId);
		if (statusPresent) {
			throw new IllegalArgumentException("Gifticon already has the status: " + boardStatusId);
		}
		SaleBoardStatusRelationship saleBoardStatusRelationship = SaleBoardStatusRelationship.builder()
				.saleBoardId(saleBoard.getGifticonId())
				.boardStatusId(boardStatusId)
				.build();
		saleBoardStatusRelationshipRepository.save(saleBoardStatusRelationship);
	}
	
	@Override
	public void addFavorite(Integer loginUserId, Integer saleBoardId) {
		saleFavoriteRepository.save(SaleFavorite.builder()
				.saleBoardId(saleBoardId)
				.userId(loginUserId)
				.build());
	}
	
	@Override
	public void deleteFavorite(Integer loginUserId, Integer saleBoardId) {
		saleFavoriteRepository.deleteBySaleBoardIdAndUserId(saleBoardId, loginUserId);
	}

}
