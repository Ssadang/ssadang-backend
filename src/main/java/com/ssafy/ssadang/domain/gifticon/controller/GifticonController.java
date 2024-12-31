package com.ssafy.ssadang.domain.gifticon.controller;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssadang.domain.gifticon.dto.GifticonRequestDto;
import com.ssafy.ssadang.domain.gifticon.dto.GifticonResponseDto;
import com.ssafy.ssadang.domain.gifticon.service.GifticonService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/gifticon")
@RequiredArgsConstructor
public class GifticonController {
	
	@Value("${server.servlet.context-path}")
	private String contextPath;
	
	private final GifticonService gifticonService;
	
	@PostMapping
	public ResponseEntity<?> save(
			@AuthenticationPrincipal Object owner,
			@Valid @ModelAttribute GifticonRequestDto gifticonRequestDto) {
		Integer ownerId = 1;
		GifticonResponseDto gifticonResponseDto = gifticonService.save(ownerId, gifticonRequestDto);
		return ResponseEntity.created(URI.create(contextPath + "/api/v1/gifticon/" + gifticonResponseDto.getGifticonId()))
				.body(gifticonResponseDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(
			@AuthenticationPrincipal Object owner,
			@PathVariable Integer id) {
		Integer ownerId = 1;
		return ResponseEntity.ok(gifticonService.findById(ownerId, id));
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(
			@AuthenticationPrincipal Object owner,
			@RequestParam Integer gifticonId) {
		Integer ownerId = 1;
		return ResponseEntity.ok(gifticonService.findByOwnerId(ownerId));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(
			@AuthenticationPrincipal Object owner,
			@PathVariable Integer id) {
		Integer ownerId = 1;
		gifticonService.deleteById(ownerId, id);
		return ResponseEntity.ok(null);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> setStatusById(
			@AuthenticationPrincipal Object owner,
			@PathVariable Integer id, @RequestBody Map<String, Integer> status) {
		Integer ownerId = 1;
		gifticonService.setStatusById(ownerId, id, status);
		return ResponseEntity.ok(null);
	}

}
