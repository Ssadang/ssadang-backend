package com.ssafy.ssadang.domain.chat.controller;

import com.ssafy.ssadang.domain.chat.dto.ChatRoomRequestDto;
import com.ssafy.ssadang.domain.chat.dto.ChatRoomResponseDto;
import com.ssafy.ssadang.domain.chat.entity.ChatRoom;
import com.ssafy.ssadang.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
    public ResponseEntity<Object> createChatRoom(@RequestBody @Validated ChatRoomRequestDto requestDto) {
        Optional<ChatRoom> existingChatRoom = chatRoomService.getChatRoomByBuyerIdAndSaleBoardId(requestDto.getBuyerId(), requestDto.getSaleBoardId());
        if(existingChatRoom.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "이미 존재하는 채팅방 입니다.", "chatRoomId", existingChatRoom.get().getId()));

        }
        ChatRoomResponseDto responseDto = chatRoomService.createChatRoom(requestDto);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<String> deleteChatRoom(@PathVariable Integer chatRoomId) {
        chatRoomService.deleteChatRoom(chatRoomId);
        return ResponseEntity.ok("채팅방이 삭제되었습니다.");
    }

}
