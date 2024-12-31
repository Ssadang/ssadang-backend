package com.ssafy.ssadang.domain.chat.repository;

import com.ssafy.ssadang.domain.chat.collection.LastMessage;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LastMessageRepository extends MongoRepository<LastMessage, String> {
    //기존 채팅방있는가 확인
    Optional<LastMessage> findByChatTypeAndSenderIdsContainingAndSaleBoardIdAndShareBoardId(Integer chatType, Integer userId, Integer saleBoardId, Integer shareBoardId);
    List<LastMessage> findBySenderIdsContaining(Integer userId, Sort sort);
}
