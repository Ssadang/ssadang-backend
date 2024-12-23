package com.ssafy.ssadang.domain.chat.service;

import com.ssafy.ssadang.domain.chat.collection.LastMessage;
import com.ssafy.ssadang.domain.chat.repository.LastMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LastMessageServiceImpl implements LastMessageService {
    private final LastMessageRepository lastMessageRepository;

    @Override
    public List<LastMessage> getChatRoomsByUserId(Integer userId) {
        return lastMessageRepository.findBySenderIdsContains(userId);
    }

    @Override
    public void updateLastMessage(Integer chatRoomId, String content, LocalDateTime createDate, Integer senderId) {
        Optional<LastMessage> existLastMessage = lastMessageRepository.findByChatRoomId(chatRoomId);
        LastMessage lastMessage;

        if (existLastMessage.isPresent()) {
            lastMessage = existLastMessage.get();
            lastMessage.setContent(content);
            lastMessage.setCreateDate(createDate);
            lastMessage.getSenderIds().add(senderId);
        } else {
            lastMessage = LastMessage.builder()
                    .chatRoomId(chatRoomId)
                    .content(content)
                    .createDate(createDate)
                    .senderIds(new HashSet<>(Set.of(senderId)))
                    .build();
        }
        lastMessageRepository.save(lastMessage);
    }
}
