package com.ssafy.ssadang.domain.gifticon.service;

import com.ssafy.ssadang.domain.gifticon.dto.GifticonRequestDto;
import com.ssafy.ssadang.domain.gifticon.dto.GifticonResponseDto;

public interface GifticonService {
	
	GifticonResponseDto save(GifticonRequestDto gifticonDto);

}
