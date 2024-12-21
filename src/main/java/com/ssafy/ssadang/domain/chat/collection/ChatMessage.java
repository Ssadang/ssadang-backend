package com.ssafy.ssadang.domain.chat.collection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Document(collection = "chatMessage")
public class ChatMessage {
    @Id
    private String id;
    private Integer sender;
    private String content;
    private LocalDateTime createDate;
    private Integer chatRoomId;

    @Builder
    public ChatMessage(Integer sender, String content, LocalDateTime createDate, Integer chatRoomId) {
        this.sender = sender;
        this.content = content;
        this.createDate = createDate;
        this.chatRoomId = chatRoomId;
    }

}
