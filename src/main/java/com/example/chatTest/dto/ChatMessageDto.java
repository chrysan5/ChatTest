package com.example.chatTest.dto;

import com.example.chatTest.model.ChatMessage;
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
    private String imageurl;
    private boolean isDelete;
    private Long chatroomId;

    public ChatMessageDto(ChatMessage chatMessage){
        this.chatMessageId = chatMessage.getChatMessageId();
        this.senderId = chatMessage.getSenderId();
        this.nickname = chatMessage.getNickname();
        this.message = chatMessage.getMessage();
        this.type = chatMessage.getType().toString();
        this.chatroomId = chatMessage.getChatroom().getChatroomId();
    }
}
