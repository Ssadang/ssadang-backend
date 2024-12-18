package com.ssafy.ssadang.chat.service;

import com.ssafy.ssadang.chat.dto.ChatRoomRequestDto;
import com.ssafy.ssadang.chat.dto.ChatRoomResponseDto;
import com.ssafy.ssadang.chat.entity.ChatRoom;
import com.ssafy.ssadang.chat.repository.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{
    private final ChatRoomRepository chatRoomRepository;


    @Override
    @Transactional
    public ChatRoomResponseDto createChatRoom(ChatRoomRequestDto requestDto) {
        ChatRoom chatRoom = ChatRoom.builder()
                .sellerId(requestDto.getSellerId())
                .buyerId(requestDto.getBuyerId())
                .chatType(requestDto.getChatType())
                .saleBoardId(requestDto.getSaleBoardId())
                .shareBoardId(requestDto.getShareBoardId())
                .createDate(LocalDateTime.now())
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        return ChatRoomResponseDto.builder()
                .chatId(savedChatRoom.getId())
                .sellerId(savedChatRoom.getSellerId())
                .buyerId(savedChatRoom.getBuyerId())
                .chatType(savedChatRoom.getChatType())
                .saleBoardId(savedChatRoom.getSaleBoardId())
                .shareBoardId(savedChatRoom.getShareBoardId())
                .createDate(savedChatRoom.getCreateDate())
                .build();
    }


}
