package com.ssafy.ssadang.domain.gifticon.controller;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<?> save(@Valid @ModelAttribute GifticonRequestDto gifticonRequestDto) {
		GifticonResponseDto gifticonResponseDto = gifticonService.save(gifticonRequestDto);
		return ResponseEntity.created(URI.create(contextPath + "/api/v1/gifticon/" + gifticonResponseDto.getGifticonId()))
				.body(gifticonResponseDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(gifticonService.findById(id));
	}
	
	@GetMapping("/owner/{ownerId}")
	public ResponseEntity<?> findAllByOwnerId(@PathVariable Integer ownerId) {
		return ResponseEntity.ok(gifticonService.findAllByOwnerId(ownerId));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id) {
		gifticonService.deleteById(id);
		return ResponseEntity.ok(null);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> setStatusById(@PathVariable Integer id, @RequestBody Map<String, Integer> status) {
		gifticonService.setStatusById(id, status);
		return ResponseEntity.ok(null);
	}

}
