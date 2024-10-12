package com.example.chatTest.repository;

import com.example.chatTest.model.ChatMessage;
import com.example.chatTest.model.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByChatroom(Chatroom chatroom);
}
