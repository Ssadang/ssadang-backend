package com.ssafy.ssadang.domain.chat.service;

import com.ssafy.ssadang.domain.chat.collection.LastMessage;
import com.ssafy.ssadang.domain.chat.dto.LastMessageRequestDto;
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
    public Optional<LastMessage> findLastMessageByChatRoomId(Integer chatRoomId) {
        return lastMessageRepository.findByChatRoomId(chatRoomId);
    }

    @Override
    public void saveLastMessage(LastMessageRequestDto requestDto, Integer loginUserId) {
        Set<Integer> senderIds = new HashSet<>();
        senderIds.add(requestDto.getSenderId());
        senderIds.add(loginUserId);

        lastMessageRepository.findByChatRoomId(requestDto.getChatRoomId())
                .ifPresentOrElse(
                        existingMessage -> {
                            existingMessage.setContent(requestDto.getContent());
                            existingMessage.setCreateDate(LocalDateTime.now());
                            existingMessage.getSenderIds().addAll(senderIds);
                            lastMessageRepository.save(existingMessage);
                        },
                        () -> {
                            LastMessage newLastMessage = LastMessage.builder()
                                    .chatRoomId(requestDto.getChatRoomId())
                                    .content(requestDto.getContent())
                                    .createDate(LocalDateTime.now())
                                    .senderIds(senderIds)
                                    .chatType(requestDto.getChatType())
                                    .saleBoardId(requestDto.getSaleBoardId())
                                    .shareBoardId(requestDto.getShareBoardId())
                                    .unReadCount(0)
                                    .build();
                            lastMessageRepository.save(newLastMessage);
                        }
                );


    }

    @Override
    public List<LastMessage> findChatRoomsByUserId(Integer userId) {
        return lastMessageRepository.findBySenderIdsContaining(userId);
    }

    @Override
    public void leaveChatRoom(Integer chatRoomId, Integer userId) {
        Optional<LastMessage> lastMessage = lastMessageRepository.findByChatRoomId(chatRoomId);

        if (lastMessage.isPresent()) {
            LastMessage lastMessageEntity = lastMessage.get();
            Set<Integer> senderIds = lastMessageEntity.getSenderIds();

            senderIds.remove(userId);

            if (senderIds.isEmpty()) {
                lastMessageRepository.delete(lastMessageEntity);
            } else {
                lastMessageEntity.setSenderIds(senderIds);
                lastMessageRepository.save(lastMessageEntity);
            }
        } else {
            throw new IllegalArgumentException("Chat room not found for chatRoomId: " + chatRoomId);
        }
    }


}
