package com.ssafy.ssadang.domain.gifticon.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssadang.domain.gifticon.dto.GifticonRequestDto;
import com.ssafy.ssadang.domain.gifticon.dto.GifticonResponseDto;
import com.ssafy.ssadang.domain.gifticon.service.GifticonService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/gifticon")
@RequiredArgsConstructor
public class GifticonController {
	
	private final GifticonService gifticonService;
	
	@PostMapping
	public GifticonResponseDto save(@ModelAttribute GifticonRequestDto gifticonRequestDto) {
		return gifticonService.save(gifticonRequestDto);
	}

}
