package com.ssafy.ssadang.domain.trade.domain.share.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShareBoardDetailResponseDto {
	
	private ShareBoardDto shareBoard;
	private List<ShareBoardDto> sameAuthorShareBoards;
	private List<ShareBoardDto> similarShareBoards;

}
