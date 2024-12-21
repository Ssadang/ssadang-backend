package com.ssafy.ssadang.domain.chat.repository;

import com.ssafy.ssadang.domain.chat.collection.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
}
