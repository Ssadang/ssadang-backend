package com.ssafy.ssadang.domain.chat.service;

import com.ssafy.ssadang.domain.chat.collection.LastMessage;
import com.ssafy.ssadang.domain.chat.dto.LastMessageRequestDto;
import com.ssafy.ssadang.domain.chat.repository.LastMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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
    public String createOrFindChatRoom(LastMessageRequestDto requestDto, Integer loginUserId) {
        Optional<LastMessage> existingRoom = lastMessageRepository.findByChatTypeAndSenderIdsContainingAndSaleBoardIdAndShareBoardId(requestDto.getChatType(), loginUserId, requestDto.getSaleBoardId(), requestDto.getShareBoardId());

        if (existingRoom.isPresent()) {
            return existingRoom.get().getId();
        } else {
            Set<Integer> senderIds = new HashSet<>();
            senderIds.add(requestDto.getSenderId());
            senderIds.add(loginUserId);

            LastMessage newLastMessage = LastMessage.builder()
                    .content(requestDto.getContent())
                    .createDate(LocalDateTime.now())
                    .senderIds(senderIds)
                    .chatType(requestDto.getChatType())
                    .saleBoardId(requestDto.getSaleBoardId())
                    .shareBoardId(requestDto.getShareBoardId())
                    .unReadCount(0)
                    .build();

            LastMessage savedMessage = lastMessageRepository.save(newLastMessage);
            return savedMessage.getId();
        }
    }

    @Override
    public List<LastMessage> findChatRoomsByUserId(Integer userId) {
        return lastMessageRepository.findBySenderIdsContaining(userId, Sort.by(Sort.Direction.DESC, "createDate"));
    }

    @Override
    public void leaveChatRoom(String id, Integer userId) {
        Optional<LastMessage> lastMessage = lastMessageRepository.findById(id);

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
            throw new IllegalArgumentException("Chat room not found for chatRoomId: " + id);
        }
    }

    @Override
    public void updateLastMessage(String chatRoomId, String content, LocalDateTime createDate) {
        Optional<LastMessage> lastMessageOptional = lastMessageRepository.findById(chatRoomId);
        if(lastMessageOptional.isPresent()) {
            LastMessage lastMessage = lastMessageOptional.get();
            lastMessage.setContent(content);
            lastMessage.setCreateDate(createDate);
            lastMessageRepository.save(lastMessage);
        }else {
            throw new IllegalArgumentException("채팅방을 찾을 수 없습니다." + chatRoomId);
        }

    }


}
