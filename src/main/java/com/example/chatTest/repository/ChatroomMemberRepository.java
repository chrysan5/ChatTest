package com.example.chatTest.repository;

import com.example.chatTest.model.Chatroom;
import com.example.chatTest.model.ChatroomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatroomMemberRepository extends JpaRepository<ChatroomMember, Long> {
    List<ChatroomMember> findAllByUserIdAndIsDeleteFalse(Long userId);

    ChatroomMember findByChatroom(Chatroom chatroom);

    Optional<ChatroomMember> findByUserIdAndChatroom(Long userId, Chatroom chatroom);

    List<ChatroomMember> findAllByChatroom(Chatroom chatroom);
}
