package com.ssafy.ssadang.domain.chat.repository;

import com.ssafy.ssadang.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository  extends JpaRepository<ChatRoom, Integer> {
    List<ChatRoom> findAllBySellerIdOrBuyerId(Integer sellerId, Integer buyerId);


}
