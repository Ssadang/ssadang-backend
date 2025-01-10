package com.ssafy.ssadang.domain.trade.domain.share.controller;

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
	public ResponseEntity<?> upload(@AuthenticationPrincipal UserPrinciple userPrinciple,
			@Valid @ModelAttribute ShareBoardRequestDto shareBoardRequestDto) {
		ShareBoardDetailResponseDto shareBoardResponseDto = shareBoardService.upload(userPrinciple.getUserId(), shareBoardRequestDto);
		return ResponseEntity
				.created(URI.create(
						contextPath + "/api/v1/share-board/" + shareBoardResponseDto.getShareBoard().getShareBoardId()))
				.body(shareBoardResponseDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> view(@AuthenticationPrincipal UserPrinciple userPrinciple,
			@PathVariable Integer id) {
		return ResponseEntity.ok(shareBoardService.view(userPrinciple.getUserId(), id));
	}
	
	@GetMapping
	public ResponseEntity<?> list(@AuthenticationPrincipal UserPrinciple userPrinciple,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer cursorId) {
		return ResponseEntity.ok(shareBoardService.list(userPrinciple.getUserId(), keyword, cursorId));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@AuthenticationPrincipal UserPrinciple userPrinciple,
			@PathVariable Integer id) {
		shareBoardService.deleteById(userPrinciple.getUserId(), id);
		return ResponseEntity.ok(null);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> setStatusById(@AuthenticationPrincipal UserPrinciple userPrinciple,
			@PathVariable Integer id, @RequestBody Map<String, Integer> status) {
		shareBoardService.setStatusById(userPrinciple.getUserId(), id, status);
		return ResponseEntity.ok(null);
	}
	
	@PostMapping("/{id}/favorite")
	public ResponseEntity<?> addFavorite(@AuthenticationPrincipal UserPrinciple userPrinciple,
			@PathVariable Integer id) {
		shareBoardService.addFavorite(userPrinciple.getUserId(), id);
		return ResponseEntity.ok(null);
	}
	
	@DeleteMapping("/{id}/favorite")
	public ResponseEntity<?> deleteFavorite(@AuthenticationPrincipal UserPrinciple userPrinciple,
			@PathVariable Integer id) {
		shareBoardService.deleteFavorite(userPrinciple.getUserId(), id);
		return ResponseEntity.ok(null);
	}
	
	@PostMapping("/{id}/participation")
	public ResponseEntity<?> participateGame(@AuthenticationPrincipal UserPrinciple userPrinciple,
			@PathVariable Integer id) {
		shareBoardService.participateGame(userPrinciple.getUserId(), id);
		return ResponseEntity.ok(null);
	}
	
	@DeleteMapping("/{id}/participation")
	public ResponseEntity<?> exitGame(@AuthenticationPrincipal UserPrinciple userPrinciple,
			@PathVariable Integer id) {
		shareBoardService.exitGame(userPrinciple.getUserId(), id);
		return ResponseEntity.ok(null);
		
	}

}
