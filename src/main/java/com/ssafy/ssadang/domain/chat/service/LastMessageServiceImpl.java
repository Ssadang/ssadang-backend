package com.ssafy.ssadang.domain.chat.service;

import com.ssafy.ssadang.domain.chat.collection.LastMessage;
import com.ssafy.ssadang.domain.chat.dto.LastMessageRequestDto;
import com.ssafy.ssadang.domain.chat.repository.LastMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
                    .unReadCounts(null)
                    .build();

            LastMessage savedMessage = lastMessageRepository.save(newLastMessage);
            return savedMessage.getId();
        }
    }

    @Override
    public List<LastMessage> findChatRoomsByUserId(Integer userId) {
        List<LastMessage> emptyChatRooms = lastMessageRepository.findBySenderIdsContainingAndContentIsNull(userId);
        if(!emptyChatRooms.isEmpty()) {
            lastMessageRepository.deleteAll(emptyChatRooms);
            System.out.println("빈채팅방 처리 " + emptyChatRooms.size());
        }
        List<LastMessage> chatRooms = lastMessageRepository.findBySenderIdsContaining(userId, Sort.by(Sort.Direction.DESC, "createDate"));
        for (LastMessage chatRoom : chatRooms) {
            Map<Integer, Integer> unReadCounts = chatRoom.getUnReadCounts();
            if (unReadCounts != null && unReadCounts.containsKey(userId)) {
                chatRoom.setUnReadCounts(Map.of(userId, unReadCounts.get(userId)));
            } else {
                chatRoom.setUnReadCounts(Map.of(userId, 0));
            }
        }
        return chatRooms;
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




}
