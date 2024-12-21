package com.ssafy.ssadang.domain.chat.service;

import com.ssafy.ssadang.domain.chat.collection.ChatMessage;
import com.ssafy.ssadang.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.ssadang.domain.chat.dto.ChatMessageResponseDto;

import java.util.List;

public interface ChatMessageService {
    ChatMessageResponseDto sendMessage(ChatMessageRequestDto request, Integer chatRoomId);
    List<ChatMessage> getChatMessagesByChatRoomId(Integer chatRoomId);

}
