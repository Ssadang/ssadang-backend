package com.ssafy.ssadang.domain.chat.collection;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@Document(collection = "lastMessage")
public class LastMessage {
    @Id
    private String id;
    private String content;
    private LocalDateTime createDate;
    private Set<Integer> senderIds;
    private Integer chatType;
    private Integer saleBoardId;
    private Integer shareBoardId;
    private Integer unReadCount;

}
