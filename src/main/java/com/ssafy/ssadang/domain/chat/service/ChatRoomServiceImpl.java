package com.ssafy.ssadang.domain.chat.service;

import com.ssafy.ssadang.domain.chat.dto.ChatRoomRequestDto;
import com.ssafy.ssadang.domain.chat.dto.ChatRoomResponseDto;
import com.ssafy.ssadang.domain.chat.entity.ChatRoom;
import com.ssafy.ssadang.domain.chat.repository.ChatRoomRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    @Override
    @Transactional
    public void deleteChatRoom(Integer chatRoomId) {
        if (!chatRoomRepository.existsById(chatRoomId)) {
            throw new RuntimeException("채팅방이 존재하지 않습니다.");
        }
        chatRoomRepository.deleteById(chatRoomId); // 삭제 수행
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatRoomResponseDto> getChatRoomsByUserId(Integer userId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllBySellerIdOrBuyerId(userId, userId);

        return chatRooms.stream()
                .map(chatRoom -> ChatRoomResponseDto.builder()
                        .chatId(chatRoom.getId())
                        .sellerId(chatRoom.getSellerId())
                        .buyerId(chatRoom.getBuyerId())
                        .chatType(chatRoom.getChatType())
                        .saleBoardId(chatRoom.getSaleBoardId())
                        .shareBoardId(chatRoom.getShareBoardId())
                        .createDate(chatRoom.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }


}
