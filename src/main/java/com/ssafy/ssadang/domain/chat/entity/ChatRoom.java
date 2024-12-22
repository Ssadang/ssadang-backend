package com.ssafy.ssadang.domain.chat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "chats")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Integer id;
    @Column(name = "seller_id", nullable = false)
    private Integer sellerId;
    @Column(name = "buyer_id", nullable = false)
    private Integer buyerId;
    @Column(name = "chat_type", nullable = false)
    private Integer chatType;
    @Column(name = "sale_board_id")
    private Integer saleBoardId;
    @Column(name = "share_board_id")
    private Integer shareBoardId;
    @Column(name = "create_date", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

}
