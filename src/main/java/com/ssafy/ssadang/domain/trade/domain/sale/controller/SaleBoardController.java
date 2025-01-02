package com.ssafy.ssadang.domain.trade.domain.sale.controller;

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

import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardRequestDto;
import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardDetailResponseDto;
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
	public ResponseEntity<?> upload(@AuthenticationPrincipal Object author,
			@Valid @ModelAttribute SaleBoardRequestDto saleBoardRequestDto) {
		// 로그인 구현 전까지 임시로 authorId 1 사용
		Integer authorId = 1;
		SaleBoardDetailResponseDto saleBoardResponseDto = saleBoardService.upload(authorId, saleBoardRequestDto);
		return ResponseEntity
				.created(URI.create(
						contextPath + "/api/v1/sale-board/" + saleBoardResponseDto.getSaleBoard().getSaleBoardId()))
				.body(saleBoardResponseDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> view(@AuthenticationPrincipal Object loginUser,
			@PathVariable Integer id) {
		Integer loginUserId = 1;
		return ResponseEntity.ok(saleBoardService.view(loginUserId, id));
	}
	
	@GetMapping
	public ResponseEntity<?> list(@AuthenticationPrincipal Object loginUser,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer cursorId) {
		Integer loginUserId = 1;
		return ResponseEntity.ok(saleBoardService.list(loginUserId, keyword, cursorId));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@AuthenticationPrincipal Object loginUser,
			@PathVariable Integer id) {
		Integer loginUserId = 1;
		saleBoardService.deleteById(loginUserId, id);
		return ResponseEntity.ok(null);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> setStatusById(@AuthenticationPrincipal Object loginUser,
			@PathVariable Integer id, @RequestBody Map<String, Integer> status) {
		Integer loginUserId = 1;
		saleBoardService.setStatusById(loginUserId, id, status);
		return ResponseEntity.ok(null);
	}

}
