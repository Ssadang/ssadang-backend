package com.ssafy.ssadang.domain.gifticon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssadang.domain.gifticon.dto.GifticonRequestDto;
import com.ssafy.ssadang.domain.gifticon.service.GifticonService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/gifticon")
@RequiredArgsConstructor
public class GifticonController {
	
	private final GifticonService gifticonService;
	
	@PostMapping
	public ResponseEntity<?> save(@ModelAttribute GifticonRequestDto gifticonRequestDto) {
		return ResponseEntity.ok(gifticonService.save(gifticonRequestDto));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(gifticonService.findById(id));
	}
	
	@GetMapping("/owner/{owner}")
	public ResponseEntity<?> findAllByOwnerId(@PathVariable Integer ownerId) {
		return ResponseEntity.ok(gifticonService.findAllByOwnerId(ownerId));
	}

}
