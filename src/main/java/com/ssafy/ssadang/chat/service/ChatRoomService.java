package com.ssafy.ssadang.chat.service;

import com.ssafy.ssadang.chat.dto.ChatRoomRequestDto;
import com.ssafy.ssadang.chat.dto.ChatRoomResponseDto;

public interface ChatRoomService {
    ChatRoomResponseDto createChatRoom(ChatRoomRequestDto requestDto);

}
