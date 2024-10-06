package com.example.chatTest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;


@SQLRestriction("is_delete = false")
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long chatMessageId;

    @Column(nullable = false)
    private String senderId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String message;

    private LocalDateTime sendTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType type;

    //private String image;

    private boolean isDelete = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private Chatroom chatroom;

}
