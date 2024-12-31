package com.ssafy.ssadang.domain.chat.service;

import com.ssafy.ssadang.domain.chat.collection.ChatMessage;
import com.ssafy.ssadang.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.ssadang.domain.chat.dto.ChatMessageResponseDto;
import com.ssafy.ssadang.domain.chat.repository.ChatMessageRepository;
import com.ssafy.ssadang.domain.chat.repository.LastMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final LastMessageService lastMessageService;
    @Override
    public ChatMessageResponseDto sendMessage(ChatMessageRequestDto request, String chatRoomId) {
        ChatMessage chatMessage = ChatMessage.builder()
                .sender(request.sender())
                .content(request.content())
                .createDate(LocalDateTime.now())
                .chatRoomId(chatRoomId)
                .isRead(false)
                .build();

        chatMessageRepository.save(chatMessage);
        lastMessageService.updateLastMessage(chatRoomId, chatMessage.getContent(), chatMessage.getCreateDate());

        return new ChatMessageResponseDto(
                chatMessage.getSender(),
                chatMessage.getContent(),
                chatMessage.getCreateDate()
        );
    }

    @Override
    public List<ChatMessage> getChatMessagesByChatRoomId(String chatRoomId) {
        return chatMessageRepository.findByChatRoomId(chatRoomId);
    }


}
