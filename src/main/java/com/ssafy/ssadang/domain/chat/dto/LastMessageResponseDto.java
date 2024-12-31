package com.ssafy.ssadang.domain.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class LastMessageResponseDto {
    private String content;
    private LocalDateTime createDate;
    private Set<Integer> senderIds;
    private Integer chatType;
    private Integer saleBoardId;
    private Integer shareBoardId;
    private Integer unReadCount;
}
