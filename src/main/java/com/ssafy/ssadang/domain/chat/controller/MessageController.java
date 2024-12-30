package com.ssafy.ssadang.domain.chat.controller;

import com.ssafy.ssadang.domain.chat.collection.ChatMessage;
import com.ssafy.ssadang.domain.chat.collection.LastMessage;
import com.ssafy.ssadang.domain.chat.dto.ChatMessageResponseDto;
import com.ssafy.ssadang.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.ssadang.domain.chat.dto.LastMessageRequestDto;
import com.ssafy.ssadang.domain.chat.service.ChatMessageService;
import com.ssafy.ssadang.domain.chat.service.LastMessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    private final  ChatMessageService chatMessageService;
    private final LastMessageService lastMessageService;

    //웹소켓 메시지 전송
    @MessageMapping("/chat/{chatRoomId}")
    @SendTo("/subscribe/chat/{chatRoomId}")
    public ChatMessageResponseDto sendMessage(ChatMessageRequestDto request, @DestinationVariable Integer chatRoomId) {
        log.info("Message received in chat room {}: {}", chatRoomId, request);
        chatMessageService.sendMessage(request, chatRoomId);
        return new ChatMessageResponseDto(request.sender(), request.content(), LocalDateTime.now());
    }

    //채팅방당 메시지 불러오기
    @GetMapping("/api/v1/chat/{chatRoomId}/messages")
    public List<ChatMessage> getMessages(@PathVariable Integer chatRoomId) {
        return chatMessageService.getChatMessagesByChatRoomId(chatRoomId);
    }
    // 채팅방 생성 또는 기존 방 확인 후 ID 반환
    @PostMapping("/api/v1/chat/room")
    public ResponseEntity<Integer> createOrFindChatRoom(@RequestBody LastMessageRequestDto requestDto, @RequestParam Integer loginUserId) {
        Optional<LastMessage> existingRoom = lastMessageService.findLastMessageByChatRoomId(requestDto.getChatRoomId());

        if (existingRoom.isPresent()) {
            return ResponseEntity.ok(existingRoom.get().getChatRoomId());
        } else {
            lastMessageService.saveLastMessage(requestDto, loginUserId);
            return ResponseEntity.ok(requestDto.getChatRoomId());
        }
    }

    //유저별 채팅방리스트 조회
    @GetMapping("/api/v1/chat/rooms")
    public ResponseEntity<List<LastMessage>> getChatRoomsByUserId(@RequestParam Integer userId) {
        List<LastMessage> chatRooms = lastMessageService.findChatRoomsByUserId(userId);

        return ResponseEntity.ok(chatRooms.isEmpty() ? new ArrayList<>() : chatRooms);
    }


    // 채팅방 나가기
    @DeleteMapping("/api/v1/chat/{chatRoomId}/leave")
    public ResponseEntity<Void> leaveChatRoom(@PathVariable Integer chatRoomId, @RequestParam Integer userId) {
        try {
            lastMessageService.leaveChatRoom(chatRoomId, userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @MessageExceptionHandler
//    public void handleException(RuntimeException e) {
//        log.info("Exception: {}", e.getMessage());
//    }
}
