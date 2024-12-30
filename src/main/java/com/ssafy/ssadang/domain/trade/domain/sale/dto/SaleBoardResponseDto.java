package com.ssafy.ssadang.domain.trade.domain.sale.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SaleBoardResponseDto {
	
	private SaleBoardDto saleBoard;
	private List<SaleBoardDto> sameAuthorSaleBoards;
	private List<SaleBoardDto> similarSaleBoards;

}
