package com.ssafy.ssadang.domain.trade.domain.sale.controller;

import java.net.URI;
import java.util.Map;

import com.ssafy.ssadang.global.security.UserPrinciple;
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

import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardDetailResponseDto;
import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardRequestDto;
import com.ssafy.ssadang.domain.trade.domain.sale.service.SaleBoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/sale-board")
@RestController
public class SaleBoardController {

	@Value("${server.servlet.context-path}")
	private String contextPath;

	private final SaleBoardService saleBoardService;

	@PostMapping
	public ResponseEntity<?> upload(@AuthenticationPrincipal UserPrinciple userPrinciple,
			@Valid @ModelAttribute SaleBoardRequestDto saleBoardRequestDto) {
		SaleBoardDetailResponseDto saleBoardResponseDto = saleBoardService.upload(userPrinciple.getUserId(), saleBoardRequestDto);
		return ResponseEntity
				.created(URI.create(
						contextPath + "/api/v1/sale-board/" + saleBoardResponseDto.getSaleBoard().getSaleBoardId()))
				.body(saleBoardResponseDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> view(@AuthenticationPrincipal UserPrinciple userPrinciple,
			@PathVariable Integer id) {
		return ResponseEntity.ok(saleBoardService.view(userPrinciple.getUserId(), id));
	}
	
	@GetMapping
	public ResponseEntity<?> list(@AuthenticationPrincipal UserPrinciple userPrinciple,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer cursorId) {
		return ResponseEntity.ok(saleBoardService.list(userPrinciple.getUserId(), keyword, cursorId));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@AuthenticationPrincipal UserPrinciple userPrinciple,
			@PathVariable Integer id) {
		saleBoardService.deleteById(userPrinciple.getUserId(), id);
		return ResponseEntity.ok(null);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> setStatusById(@AuthenticationPrincipal UserPrinciple userPrinciple,
			@PathVariable Integer id, @RequestBody Map<String, Integer> status) {
		saleBoardService.setStatusById(userPrinciple.getUserId(), id, status);
		return ResponseEntity.ok(null);
	}
	
	@PostMapping("/{id}/favorite")
	public ResponseEntity<?> addFavorite(@AuthenticationPrincipal UserPrinciple userPrinciple,
			@PathVariable Integer id) {
		saleBoardService.addFavorite(userPrinciple.getUserId(), id);
		return ResponseEntity.ok(null);
	}
	
	@DeleteMapping("/{id}/favorite")
	public ResponseEntity<?> deleteFavorite(@AuthenticationPrincipal UserPrinciple userPrinciple,
			@PathVariable Integer id) {
		saleBoardService.deleteFavorite(userPrinciple.getUserId(), id);
		return ResponseEntity.ok(null);
	}

}
