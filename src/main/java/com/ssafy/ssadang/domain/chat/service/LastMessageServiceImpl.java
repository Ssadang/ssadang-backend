package com.ssafy.ssadang.domain.chat.service;

import com.ssafy.ssadang.domain.chat.collection.LastMessage;
import com.ssafy.ssadang.domain.chat.repository.LastMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LastMessageServiceImpl implements LastMessageService {
    private final LastMessageRepository lastMessageRepository;
    @Override
    public void updateLastMessage(Integer chatRoomId, String content, LocalDateTime createDate) {
        Optional<LastMessage> existLastMessage = lastMessageRepository.findByChatRoomId(chatRoomId);

        LastMessage lastMessage = LastMessage.builder()
                .chatRoomId(chatRoomId)
                .content(content)
                .createDate(createDate)
                .build();
        if (existLastMessage.isPresent()) {
            lastMessage.setId(existLastMessage.get().getId());
        }
        lastMessageRepository.save(lastMessage);
    }
}
