package com.example.chatTest.repository;

import com.example.chatTest.model.ChatMessage;
import com.example.chatTest.model.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByChatroom(Chatroom chatroom);

    @Modifying
    @Query("DELETE FROM ChatMessage c WHERE c.sendTime < :beforeMonth")
    void deleteAllWithSendTimeOverMonth(@Param("beforeMonth") LocalDateTime beforeMonth);
}
