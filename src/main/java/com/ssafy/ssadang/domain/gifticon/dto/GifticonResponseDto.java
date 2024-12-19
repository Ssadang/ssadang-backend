package com.ssafy.ssadang.domain.gifticon.dto;

import java.time.LocalDate;

import com.ssafy.ssadang.domain.gifticon.entity.Gifticon;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GifticonResponseDto {
	
	private Integer gifticonId;
	private Integer ownerId;
	private String imagePath;
	private LocalDate expiryDate;
	private String name;
	
	public static GifticonResponseDto fromEntity(Gifticon gifticon) {
		GifticonResponseDto gifticonDto = GifticonResponseDto.builder()
				.gifticonId(gifticon.getGifticonId())
				.ownerId(gifticon.getOwner().getUserId())
				.imagePath(gifticon.getImagePath())
				.expiryDate(gifticon.getExpiryDate())
				.name(gifticon.getName())
				.build();
		return gifticonDto;
	}

}
