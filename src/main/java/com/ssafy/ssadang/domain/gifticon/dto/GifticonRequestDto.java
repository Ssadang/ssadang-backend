package com.ssafy.ssadang.domain.gifticon.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GifticonRequestDto {
	
	@NotNull
	private Integer ownerId;
	@NotNull
	private MultipartFile image;
	@NotNull
	private LocalDate expiryDate;
	@NotNull
	private String name;

}
