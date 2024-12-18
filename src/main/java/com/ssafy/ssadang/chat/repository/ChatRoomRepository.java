package com.ssafy.ssadang.chat.repository;

import com.ssafy.ssadang.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository  extends JpaRepository<ChatRoom, Long> {


}
