package com.ssafy.ssadang.domain.chat.service;

import com.ssafy.ssadang.domain.chat.collection.LastMessage;
import com.ssafy.ssadang.domain.chat.dto.LastMessageRequestDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LastMessageService {
    String createOrFindChatRoom(LastMessageRequestDto requestDto, Integer loginUserId);
    List<LastMessage> findChatRoomsByUserId(Integer userId);
    void leaveChatRoom(String id, Integer userId);
}
