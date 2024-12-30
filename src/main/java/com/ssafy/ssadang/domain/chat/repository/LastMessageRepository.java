package com.ssafy.ssadang.domain.chat.repository;

import com.ssafy.ssadang.domain.chat.collection.LastMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LastMessageRepository extends MongoRepository<LastMessage, String> {
    Optional<LastMessage> findByChatRoomId(Integer chatRoomId);
    List<LastMessage> findBySenderIdsContaining(Integer userId);
}
