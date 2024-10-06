package com.example.chatTest.repository;

import com.example.chatTest.model.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
}

