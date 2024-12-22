package com.ssafy.ssadang.domain.chat.service;

import com.ssafy.ssadang.domain.chat.collection.ChatMessage;
import com.ssafy.ssadang.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.ssadang.domain.chat.dto.ChatMessageResponseDto;
import com.ssafy.ssadang.domain.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessageResponseDto sendMessage(ChatMessageRequestDto request, Integer chatRoomId) {
        ChatMessage chatMessage = ChatMessage.builder()
                .sender(request.sender())
                .content(request.content())
                .createDate(LocalDateTime.now())
                .chatRoomId(chatRoomId)
                .build();

        chatMessageRepository.save(chatMessage);

        return new ChatMessageResponseDto(
                chatMessage.getSender(),
                chatMessage.getContent(),
                chatMessage.getCreateDate()
        );
    }

    @Override
    public List<ChatMessage> getChatMessagesByChatRoomId(Integer chatRoomId) {
        return chatMessageRepository.findByChatRoomId(chatRoomId);
    }


}
