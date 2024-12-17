package com.ssafy.ssadang.domain.gifticon.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GifticonRequestDto {
	
	private Integer ownerId;
	private MultipartFile image;
	private LocalDate expiryDate;
	private String name;

}
