package com.ssafy.ssadang.domain.chat.service;

import com.ssafy.ssadang.domain.chat.dto.ChatRoomRequestDto;
import com.ssafy.ssadang.domain.chat.dto.ChatRoomResponseDto;
import com.ssafy.ssadang.domain.chat.entity.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomService {
    ChatRoomResponseDto createChatRoom(ChatRoomRequestDto requestDto);
    void deleteChatRoom(Integer chatRoomId);
    List<ChatRoomResponseDto> getChatRoomsByUserId(Integer userId);
    Optional<ChatRoom> getChatRoomByBuyerIdAndSaleBoardId(Integer buyerId, Integer saleBoardId);
}
