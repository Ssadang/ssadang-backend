package com.ssafy.ssadang.domain.chat.service;

import com.ssafy.ssadang.domain.chat.dto.ChatRoomRequestDto;
import com.ssafy.ssadang.domain.chat.dto.ChatRoomResponseDto;

import java.util.List;

public interface ChatRoomService {
    ChatRoomResponseDto createChatRoom(ChatRoomRequestDto requestDto);
    void deleteChatRoom(Integer chatRoomId);
    List<ChatRoomResponseDto> getChatRoomsByUserId(Integer userId);
}
