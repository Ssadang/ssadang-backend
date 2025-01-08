package com.ssafy.ssadang.domain.trade.domain.share.controller;

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

import com.ssafy.ssadang.domain.trade.domain.share.dto.ShareBoardDetailResponseDto;
import com.ssafy.ssadang.domain.trade.domain.share.dto.ShareBoardRequestDto;
import com.ssafy.ssadang.domain.trade.domain.share.service.ShareBoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/share-board")
@RestController
public class ShareBoardController {

	@Value("${server.servlet.context-path}")
	private String contextPath;

	private final ShareBoardService shareBoardService;

	@PostMapping
	public ResponseEntity<?> upload(@AuthenticationPrincipal Object author,
			@Valid @ModelAttribute ShareBoardRequestDto shareBoardRequestDto) {
		// 로그인 구현 전까지 임시로 authorId 1 사용
		Integer authorId = 1;
		ShareBoardDetailResponseDto shareBoardResponseDto = shareBoardService.upload(authorId, shareBoardRequestDto);
		return ResponseEntity
				.created(URI.create(
						contextPath + "/api/v1/share-board/" + shareBoardResponseDto.getShareBoard().getShareBoardId()))
				.body(shareBoardResponseDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> view(@AuthenticationPrincipal Object loginUser,
			@PathVariable Integer id) {
		Integer loginUserId = 1;
		return ResponseEntity.ok(shareBoardService.view(loginUserId, id));
	}
	
	@GetMapping
	public ResponseEntity<?> list(@AuthenticationPrincipal Object loginUser,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer cursorId) {
		Integer loginUserId = 1;
		return ResponseEntity.ok(shareBoardService.list(loginUserId, keyword, cursorId));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@AuthenticationPrincipal Object loginUser,
			@PathVariable Integer id) {
		Integer loginUserId = 1;
		shareBoardService.deleteById(loginUserId, id);
		return ResponseEntity.ok(null);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> setStatusById(@AuthenticationPrincipal Object loginUser,
			@PathVariable Integer id, @RequestBody Map<String, Integer> status) {
		Integer loginUserId = 1;
		shareBoardService.setStatusById(loginUserId, id, status);
		return ResponseEntity.ok(null);
	}
	
	@PostMapping("/{id}/favorite")
	public ResponseEntity<?> addFavorite(@AuthenticationPrincipal Object loginUser,
			@PathVariable Integer id) {
		Integer loginUserId = 1;
		shareBoardService.addFavorite(loginUserId, id);
		return ResponseEntity.ok(null);
	}
	
	@DeleteMapping("/{id}/favorite")
	public ResponseEntity<?> deleteFavorite(@AuthenticationPrincipal Object loginUser,
			@PathVariable Integer id) {
		Integer loginUserId = 1;
		shareBoardService.deleteFavorite(loginUserId, id);
		return ResponseEntity.ok(null);
	}
	
	@PostMapping("/{id}/participation")
	public ResponseEntity<?> participateGame(@AuthenticationPrincipal Object loginUser,
			@PathVariable Integer id) {
		Integer loginUserId = 1;
		return ResponseEntity.ok(null);
	}
	
	@DeleteMapping("/{id}/participation")
	public ResponseEntity<?> exitGame(@AuthenticationPrincipal Object loginUser,
			@PathVariable Integer id) {
		Integer loginUserId = 1;
		return ResponseEntity.ok(null);
		
	}

}
