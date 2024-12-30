package com.ssafy.ssadang.domain.trade.domain.sale.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.ssadang.domain.trade.domain.sale.entity.SaleBoard;
import com.ssafy.ssadang.domain.user.dto.UserDto;

import lombok.Getter;

@Getter
public class SaleBoardDto {
	
	private Integer saleBoardId;
	private String title;
	private UserDto author;
	private String description;
	private Integer price;
	private List<String> imagePaths;
	private Integer areaId;
	private Integer gifticonId;
	private Integer favoriteCount;
	private Boolean favorite;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	private Integer itemCategoryId;
	
	public SaleBoardDto(SaleBoard saleBoard, UserDto author, List<String> imagePaths,
			Integer favoriteCount, Boolean favorite) {
		this.saleBoardId = saleBoard.getSaleBoardId();
		this.title = saleBoard.getTitle();
		this.author = author;
		this.description = saleBoard.getDescription();
		this.price = saleBoard.getPrice();
		this.imagePaths = imagePaths;
		this.areaId = saleBoard.getAreaId();
		this.gifticonId = saleBoard.getGifticonId();
		this.favoriteCount = favoriteCount;
		this.favorite = favorite;
		this.createDate = saleBoard.getCreateDate();
		this.updateDate = saleBoard.getUpdateDate();
		this.itemCategoryId = saleBoard.getItemCategoryId();
	}

}
