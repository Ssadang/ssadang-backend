package com.ssafy.ssadang.domain.chat.collection;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "lastMessage")
public class LastMessage {
    @Id
    private String id;
    private Integer chatRoomId;
    private String content;
    private LocalDateTime createDate;
}
