package com.ssafy.ssadang.domain.chat.service;

import com.ssafy.ssadang.domain.chat.collection.ChatMessage;
import com.ssafy.ssadang.domain.chat.collection.LastMessage;
import com.ssafy.ssadang.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.ssadang.domain.chat.dto.ChatMessageResponseDto;
import com.ssafy.ssadang.domain.chat.repository.ChatMessageRepository;
import com.ssafy.ssadang.domain.chat.repository.LastMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final LastMessageRepository lastMessageRepository;
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
        Optional<LastMessage> chatRoomOptional = lastMessageRepository.findById(chatRoomId);
        if (chatRoomOptional.isPresent()) {
            LastMessage lastMessage = chatRoomOptional.get();
            lastMessage.setContent(request.content());
            lastMessage.setCreateDate(LocalDateTime.now());

            Map<Integer, Integer> unReadCounts = lastMessage.getUnReadCounts();
            if (unReadCounts == null) {
                unReadCounts = new HashMap<>();
            }

            for (Integer userId : lastMessage.getSenderIds()) {
                if (!userId.equals(request.sender())) {
                    unReadCounts.put(userId, unReadCounts.getOrDefault(userId, 0) + 1);
//                    System.out.println("userId : " + userId + " unReadCounts : " + unReadCounts.get(userId));
                }
            }
            lastMessage.setUnReadCounts(unReadCounts);
            lastMessageRepository.save(lastMessage);
        }

        return new ChatMessageResponseDto(
                chatMessage.getSender(),
                chatMessage.getContent(),
                chatMessage.getCreateDate()
        );
    }

    @Override
    public List<ChatMessage> getChatMessagesByChatRoomId(String chatRoomId, Integer userId) {
        List<ChatMessage> messages = chatMessageRepository.findByChatRoomId(chatRoomId);

        Optional<LastMessage> lastMessageOptional = lastMessageRepository.findById(chatRoomId);
        if (lastMessageOptional.isPresent()) {
            LastMessage lastMessage = lastMessageOptional.get();
            Map<Integer, Integer> unReadCounts = lastMessage.getUnReadCounts();
            if (unReadCounts != null && unReadCounts.containsKey(userId)) {
                unReadCounts.put(userId, 0);
                lastMessage.setUnReadCounts(unReadCounts);
                lastMessageRepository.save(lastMessage);
            }
        }

        return messages;
    }


}
