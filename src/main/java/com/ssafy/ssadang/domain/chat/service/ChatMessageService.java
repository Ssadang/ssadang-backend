package com.ssafy.ssadang.domain.chat.service;

import com.ssafy.ssadang.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.ssadang.domain.chat.dto.ChatMessageResponseDto;

public interface ChatMessageService {
    ChatMessageResponseDto sendMessage(ChatMessageRequestDto request, Integer chatRoomId);

}
