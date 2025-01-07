package com.ssafy.ssadang.domain.trade.domain.share.service;

import java.util.List;
import java.util.Map;

import com.ssafy.ssadang.domain.trade.domain.share.dto.ShareBoardDto;
import com.ssafy.ssadang.domain.trade.domain.share.dto.ShareBoardRequestDto;
import com.ssafy.ssadang.domain.trade.domain.share.dto.ShareBoardDetailResponseDto;

public interface ShareBoardService {
	
	ShareBoardDetailResponseDto upload(Integer authorId, ShareBoardRequestDto shareBoardRequestDto);
	
	List<ShareBoardDto> findAllDtoByAuthorId(Integer authorId, Integer currentUserId);
	
	List<ShareBoardDto> findAllDtoByItemCategoryId(Integer itemCategoryId, Integer currentUserId);
	
	ShareBoardDetailResponseDto view(Integer loginUserId, Integer shareBoardid);

	List<ShareBoardDto> list(Integer loginUserId, String keyword, Integer cursorId);
	
	void deleteById(Integer loginUserId, Integer shareBoardId);
	
	void setStatusById(Integer loginUserId, Integer shareBoardId, Map<String, Integer> status);
	
	void addFavorite(Integer loginUserId, Integer shareBoardId);
	
	void deleteFavorite(Integer loginUserId, Integer shareBoardId);
	
	void participateGame(Integer loginuserId, Integer shareBoardId);
	
	void exitGame(Integer loginuserId, Integer shareBoardId);

}
