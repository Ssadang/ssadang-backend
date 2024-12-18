package com.ssafy.ssadang.domain.chat.dto;

import lombok.Getter;

@Getter
public class ChatRoomRequestDto {

    private Integer sellerId;
    private Integer buyerId;
    private Integer chatType; // 0: 판매, 1: 나눔
    private Integer saleBoardId;
    private Integer shareBoardId;

}
