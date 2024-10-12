package com.example.chatTest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "chatroom_members")
public class ChatroomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_member_id")
    private Long chatroomMemberId;

    private boolean isDelete = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private Chatroom chatroom;

    private Long userId;

    public ChatroomMember(Chatroom chatroom, Long userId) {
        this.chatroom = chatroom;
        this.userId = userId;
    }
}
