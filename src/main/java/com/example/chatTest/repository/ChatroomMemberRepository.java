package com.example.chatTest.repository;

import com.example.chatTest.model.ChatroomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomMemberRepository extends JpaRepository<ChatroomMember, Long> {
    void deleteByUser_Id(Long userId);
}
