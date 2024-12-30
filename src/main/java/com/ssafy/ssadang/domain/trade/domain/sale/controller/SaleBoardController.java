package com.ssafy.ssadang.domain.trade.domain.sale.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardRequestDto;
import com.ssafy.ssadang.domain.trade.domain.sale.dto.SaleBoardResponseDto;
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
	public ResponseEntity<?> upload(@AuthenticationPrincipal Object authorPrincipal,
			@Valid @ModelAttribute SaleBoardRequestDto saleBoardRequestDto) {
		// 로그인 구현 전까지 임시로 authorId 1 사용
		Integer authorId = 1;
		SaleBoardResponseDto saleBoardResponseDto = saleBoardService.upload(authorId, saleBoardRequestDto);
		return ResponseEntity
				.created(URI.create(
						contextPath + "/api/v1/sale-board/" + saleBoardResponseDto.getSaleBoard().getSaleBoardId()))
				.body(saleBoardResponseDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> view(@AuthenticationPrincipal Object loginUserPrincipal,
			@PathVariable Integer id) {
		Integer loginUserId = 1;
		return ResponseEntity.ok(saleBoardService.view(loginUserId, id));
	}

}
