package com.ssafy.ssadang.domain.chat.repository;

import com.ssafy.ssadang.domain.chat.collection.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    Page<ChatMessage> findByChatRoomId(String chatRoomId, Pageable pageable);
}
