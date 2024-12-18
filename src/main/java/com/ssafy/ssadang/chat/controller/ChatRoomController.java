package com.ssafy.ssadang.chat.controller;

import com.ssafy.ssadang.chat.dto.ChatRoomRequestDto;
import com.ssafy.ssadang.chat.dto.ChatRoomResponseDto;
import com.ssafy.ssadang.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity<ChatRoomResponseDto> createChatRoom(@RequestBody @Validated ChatRoomRequestDto requestDto) {
        ChatRoomResponseDto responseDto = chatRoomService.createChatRoom(requestDto);
        return ResponseEntity.ok(responseDto);
    }

}
