package com.ssafy.ssadang.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ChatRoomResponseDto {
    private Long chatId;
    private Integer sellerId;
    private Integer buyerId;
    private Integer chatType;
    private Integer saleBoardId;
    private Integer shareBoardId;
    private LocalDateTime createDate;
}
