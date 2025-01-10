package com.ssafy.ssadang.domain.gifticon.controller;

import java.net.URI;
import java.util.Map;

import com.ssafy.ssadang.domain.user.entity.User;
import com.ssafy.ssadang.global.security.UserPrinciple;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/gifticon")
@RequiredArgsConstructor
public class GifticonController {
	
	@Value("${server.servlet.context-path}")
	private String contextPath;
	
	private final GifticonService gifticonService;
	
	@PostMapping
	public ResponseEntity<?> save(
			@AuthenticationPrincipal UserPrinciple userPrinciple,
			@Valid @ModelAttribute GifticonRequestDto gifticonRequestDto) {
		GifticonResponseDto gifticonResponseDto = gifticonService.save(userPrinciple.getUserId(), gifticonRequestDto);
		return ResponseEntity.created(URI.create(contextPath + "/api/v1/gifticon/" + gifticonResponseDto.getGifticonId()))
				.body(gifticonResponseDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(
			@AuthenticationPrincipal UserPrinciple userPrinciple,
			@PathVariable Integer id) {
		return ResponseEntity.ok(gifticonService.findById(userPrinciple.getUserId(), id));
	}
	
	@GetMapping("/expired")
	public ResponseEntity<?> findAllExpired(
			@AuthenticationPrincipal UserPrinciple userPrinciple,
			@RequestParam(required = false) Integer cursorId) {
		return ResponseEntity.ok(gifticonService.findExpiredPage(userPrinciple.getUserId(), cursorId));
	}
	
	@GetMapping("/unexpired")
	public ResponseEntity<?> findAllUnexpired(
			@AuthenticationPrincipal UserPrinciple userPrinciple,
			@RequestParam(required = false) Integer cursorId) {
		return ResponseEntity.ok(gifticonService.findUnexpiredPage(userPrinciple.getUserId(), cursorId));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(
			@AuthenticationPrincipal UserPrinciple userPrinciple,
			@PathVariable Integer id) {
		gifticonService.deleteById(userPrinciple.getUserId(), id);
		return ResponseEntity.ok(null);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> setStatusById(
			@AuthenticationPrincipal UserPrinciple userPrinciple,
			@PathVariable Integer id, @RequestBody Map<String, Integer> status) {
		gifticonService.setStatusById(userPrinciple.getUserId(), id, status);
		return ResponseEntity.ok(null);
	}

}
