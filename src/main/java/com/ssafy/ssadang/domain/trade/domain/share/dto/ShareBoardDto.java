package com.ssafy.ssadang.domain.trade.domain.share.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.ssadang.domain.trade.domain.share.entity.ShareBoard;
import com.ssafy.ssadang.domain.user.dto.UserDto;

import lombok.Getter;

@Getter
public class ShareBoardDto {
	
	private Integer shareBoardId;
	private String title;
	private UserDto author;
	private String description;
	private List<String> imagePaths;
	private Integer areaId;
	private Integer gifticonId;
	private Integer favoriteCount;
	private Boolean favorite;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	private Integer itemCategoryId;
	
	public ShareBoardDto(ShareBoard shareBoard, UserDto author, List<String> imagePaths,
			Integer favoriteCount, Boolean favorite) {
		this.shareBoardId = shareBoard.getShareBoardId();
		this.title = shareBoard.getTitle();
		this.author = author;
		this.description = shareBoard.getDescription();
		this.imagePaths = imagePaths;
		this.areaId = shareBoard.getAreaId();
		this.gifticonId = shareBoard.getGifticonId();
		this.favoriteCount = favoriteCount;
		this.favorite = favorite;
		this.createDate = shareBoard.getCreateDate();
		this.updateDate = shareBoard.getUpdateDate();
		this.itemCategoryId = shareBoard.getItemCategoryId();
	}

}
