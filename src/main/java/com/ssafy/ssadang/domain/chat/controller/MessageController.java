package com.ssafy.ssadang.domain.chat.controller;

import com.ssafy.ssadang.domain.chat.dto.ChatMessageResponseDto;
import com.ssafy.ssadang.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.ssadang.domain.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    private final  ChatMessageService chatMessageService;

    @MessageMapping("/chat/{chatRoomId}")
    @SendTo("/subscribe/chat/{chatRoomId}")
    public ChatMessageResponseDto sendMessage(ChatMessageRequestDto request, @DestinationVariable Integer chatRoomId) {
        log.info("Message received in chat room {}: {}", chatRoomId, request);
        chatMessageService.sendMessage(request, chatRoomId);
        return new ChatMessageResponseDto(request.sender(), request.content(), LocalDateTime.now());
    }

//    @MessageExceptionHandler
//    public void handleException(RuntimeException e) {
//        log.info("Exception: {}", e.getMessage());
//    }
}
