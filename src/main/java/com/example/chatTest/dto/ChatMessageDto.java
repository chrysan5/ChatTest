package com.example.chatTest.dto;

import com.example.chatTest.model.MessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageDto {
    private Long chatMessageId;
    private String senderId;
    private String nickname;
    private String message;
    private LocalDateTime sendTime;
    private String type;
    //private String image;
    private boolean isDelete;
    private Long chatroomId;
}
