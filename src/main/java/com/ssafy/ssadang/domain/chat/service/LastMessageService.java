package com.ssafy.ssadang.domain.chat.service;

import java.time.LocalDateTime;

public interface LastMessageService {
    void updateLastMessage(Integer chatRoomId, String content, LocalDateTime createDate);

}
