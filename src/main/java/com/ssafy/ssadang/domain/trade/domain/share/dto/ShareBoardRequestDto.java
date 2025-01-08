package com.ssafy.ssadang.domain.trade.domain.share.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ShareBoardRequestDto {
	
	@NotNull
	private String title;
	@NotNull
	private String description;
	private List<MultipartFile> images;
	@NotNull
	private Integer areaId;
	private Integer gifticonId;
	@NotNull
	private Integer itemCategoryId;
	private Integer maxParticipantCount;
	@NotNull
	private Boolean gameSelected;

}
