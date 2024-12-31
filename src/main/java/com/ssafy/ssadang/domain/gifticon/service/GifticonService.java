package com.ssafy.ssadang.domain.gifticon.service;

import java.util.List;
import java.util.Map;

import com.ssafy.ssadang.domain.gifticon.dto.GifticonRequestDto;
import com.ssafy.ssadang.domain.gifticon.dto.GifticonResponseDto;

public interface GifticonService {
	
	GifticonResponseDto save(Integer ownerId, GifticonRequestDto gifticonDto);
	
	GifticonResponseDto findById(Integer ownerId, Integer gifticonId);
	
	List<GifticonResponseDto> findByOwnerId(Integer ownerId);
	
	void deleteById(Integer ownerId, Integer gifticonId);
	
	void setStatusById(Integer ownerId, Integer gifticonId, Map<String, Integer> status);

}
