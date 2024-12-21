package com.ssafy.ssadang.domain.chat.dto;

import java.time.LocalDateTime;

public record ChatMessageResponse(Integer sender, String content, LocalDateTime createDate ) {
}
