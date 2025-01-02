package com.ssafy.ssadang.domain.chat.service;

import com.ssafy.ssadang.domain.chat.collection.ChatMessage;
import com.ssafy.ssadang.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.ssadang.domain.chat.dto.ChatMessageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatMessageService {
    ChatMessageResponseDto sendMessage(ChatMessageRequestDto request, String chatRoomId);
    Page<ChatMessage> getChatMessagesByChatRoomId(String chatRoomId, Integer userId, Pageable pageable);

}
