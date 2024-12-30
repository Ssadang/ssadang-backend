package com.ssafy.ssadang.domain.trade.domain.sale.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardDto;
import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardRequestDto;
import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardResponseDto;
import com.ssafy.ssadang.domain.trade.domain.sale.entity.SaleBoard;
import com.ssafy.ssadang.domain.trade.domain.sale.entity.SaleFavorite;
import com.ssafy.ssadang.domain.trade.domain.sale.entity.SaleImage;
import com.ssafy.ssadang.domain.trade.domain.sale.repository.SaleBoardRepository;
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

	private final AmazonS3Uploader amazonS3Uploader;
	
	private final UserService userService;
	
	private final SaleBoardRepository saleBoardRepository;
	private final SaleImageRepository saleImageRepository;
	private final SaleFavoriteRepository saleFavoriteRepository;
	
	@Override
	public SaleBoardResponseDto upload(Integer authorId, SaleBoardRequestDto saleBoardRequestDto) {
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
		return toSaleBoardResponseDto(savedSaleBoard, authorDto);
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
	public SaleBoardResponseDto view(Integer loginUserId, Integer saleBoardid) {
		SaleBoard saleBoard = saleBoardRepository.findById(saleBoardid).orElseThrow();
		return toSaleBoardResponseDto(saleBoard, userService.findDtoById(loginUserId));
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
	
	private SaleBoardResponseDto toSaleBoardResponseDto(SaleBoard saleBoard, UserDto loginUserDto) {
		SaleBoardDto saleBoardDto = toSaleBoardDto(saleBoard, loginUserDto.getUserId());
		List<SaleBoardDto> sameAuthorSaleBoardDtos = findAllDtoByAuthorId(saleBoard.getAuthorId(),
				loginUserDto.getUserId()).stream()
				.filter(dto -> !dto.getSaleBoardId().equals(saleBoard.getSaleBoardId()))
				.toList();
		List<SaleBoardDto> similarSaleBoardDtos = findAllDtoByItemCategoryId(saleBoard.getItemCategoryId(),
				loginUserDto.getUserId()).stream()
				.filter(dto -> !dto.getSaleBoardId().equals(saleBoard.getSaleBoardId()))
				.toList();
		return new SaleBoardResponseDto(saleBoardDto, sameAuthorSaleBoardDtos, similarSaleBoardDtos);
	}

}
