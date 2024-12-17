package com.ssafy.ssadang.domain.gifticon.dto;

import java.time.LocalDate;

import com.ssafy.ssadang.domain.gifticon.entity.Gifticon;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GifticonResponseDto {
	
	private Integer gifticonId;
//	private User owner;
	private String imagePath;
	private LocalDate expiryDate;
	private String name;
	private Boolean used;
	
	public static GifticonResponseDto fromEntity(Gifticon gifticon) {
		GifticonResponseDto gifticonDto = GifticonResponseDto.builder()
				.gifticonId(gifticon.getGifticonId())
				.imagePath(gifticon.getImagePath())
				.expiryDate(gifticon.getExpiryDate())
				.name(gifticon.getName())
				.used(gifticon.getUsed())
				.build();
		return gifticonDto;
	}

}
