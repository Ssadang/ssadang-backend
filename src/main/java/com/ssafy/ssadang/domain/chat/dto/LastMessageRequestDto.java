package com.ssafy.ssadang.domain.chat.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LastMessageRequestDto {
    private String content;
    private Integer senderId;
    private Integer chatType;
    private Integer saleBoardId;
    private Integer shareBoardId;

}
