package com.ssafy.ssadang.domain.trade.domain.sale.service;

import java.util.List;

import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardDto;
import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardRequestDto;
import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardResponseDto;

public interface SaleBoardService {
	
	SaleBoardResponseDto upload(Integer authorId, SaleBoardRequestDto saleBoardRequestDto);
	
	List<SaleBoardDto> findAllDtoByAuthorId(Integer authorId, Integer currentUserId);
	
	List<SaleBoardDto> findAllDtoByItemCategoryId(Integer itemCategoryId, Integer currentUserId);

}
