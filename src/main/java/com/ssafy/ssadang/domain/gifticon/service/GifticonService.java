package com.ssafy.ssadang.domain.gifticon.service;

import java.util.List;
import java.util.Map;

import com.ssafy.ssadang.domain.gifticon.dto.GifticonRequestDto;
import com.ssafy.ssadang.domain.gifticon.dto.GifticonResponseDto;

public interface GifticonService {
	
	GifticonResponseDto save(GifticonRequestDto gifticonDto);
	
	GifticonResponseDto findById(Integer id);
	
	List<GifticonResponseDto> findAllByOwnerId(Integer ownerId);
	
	void deleteById(Integer id);
	
	void setStatusById(Integer id, Map<String, Integer> status);

}
