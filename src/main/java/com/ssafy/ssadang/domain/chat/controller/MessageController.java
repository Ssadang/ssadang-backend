package com.ssafy.ssadang.domain.chat.controller;

import com.ssafy.ssadang.domain.chat.dto.ChatMessageResponse;
import com.ssafy.ssadang.domain.chat.dto.ChatMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    @MessageMapping("/chat/{chatRoomId}")
    @SendTo("/subscribe/chat/{chatRoomId}")
    public ChatMessageResponse sendMessage(ChatMessageRequest request, @DestinationVariable Integer chatRoomId) {
        log.info("Message received in chat room {}: {}", chatRoomId, request);
        LocalDateTime now = LocalDateTime.now();
        return new ChatMessageResponse(request.sender(), request.content(), now);
    }

//    @MessageExceptionHandler
//    public void handleException(RuntimeException e) {
//        log.info("Exception: {}", e.getMessage());
//    }
}
