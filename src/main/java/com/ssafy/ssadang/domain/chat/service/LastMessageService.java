package com.ssafy.ssadang.domain.chat.service;

import com.ssafy.ssadang.domain.chat.collection.LastMessage;

import java.time.LocalDateTime;
import java.util.List;

public interface LastMessageService {
    List<LastMessage> getChatRoomsByUserId(Integer userId);
    void updateLastMessage(Integer chatRoomId, String content, LocalDateTime createDate, Integer senderId);

}
