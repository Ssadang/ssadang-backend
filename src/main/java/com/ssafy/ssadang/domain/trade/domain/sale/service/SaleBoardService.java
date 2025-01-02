package com.ssafy.ssadang.domain.trade.domain.sale.service;

import java.util.List;
import java.util.Map;

import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardDto;
import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardRequestDto;
import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardDetailResponseDto;

public interface SaleBoardService {
	
	SaleBoardDetailResponseDto upload(Integer authorId, SaleBoardRequestDto saleBoardRequestDto);
	
	List<SaleBoardDto> findAllDtoByAuthorId(Integer authorId, Integer currentUserId);
	
	List<SaleBoardDto> findAllDtoByItemCategoryId(Integer itemCategoryId, Integer currentUserId);
	
	SaleBoardDetailResponseDto view(Integer loginUserId, Integer saleBoardid);

	List<SaleBoardDto> list(Integer loginUserId, String keyword, Integer cursorId);
	
	void deleteById(Integer loginUserId, Integer saleBoardId);
	
	void setStatusById(Integer loginUserId, Integer saleBoardId, Map<String, Integer> status);

}
