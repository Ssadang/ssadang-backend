package com.ssafy.ssadang.domain.chat.controller;

import com.ssafy.ssadang.domain.chat.dto.ChatRoomRequestDto;
import com.ssafy.ssadang.domain.chat.dto.ChatRoomResponseDto;
import com.ssafy.ssadang.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;


    @GetMapping("/{userId}")
    public ResponseEntity<List<ChatRoomResponseDto>> getChatRoomsByUserId(@PathVariable Integer userId) {
        List<ChatRoomResponseDto> responseDtoList = chatRoomService.getChatRoomsByUserId(userId);
        return ResponseEntity.ok(responseDtoList);
    }
    @PostMapping
    public ResponseEntity<ChatRoomResponseDto> createChatRoom(@RequestBody @Validated ChatRoomRequestDto requestDto) {
        ChatRoomResponseDto responseDto = chatRoomService.createChatRoom(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<String> deleteChatRoom(@PathVariable Integer chatRoomId) {
        chatRoomService.deleteChatRoom(chatRoomId);
        return ResponseEntity.ok("채팅방이 삭제되었습니다.");
    }

}
