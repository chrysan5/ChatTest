package com.example.chatTest.controller;

import com.example.chatTest.dto.ChatMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatMessageController {

    private final SimpMessagingTemplate template;

    @Autowired
    public ChatMessageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/chat/enter")
    public void enter(ChatMessageDto message){
        message.setMessage(message.getSenderId() + "님이 입장하셨습니다.");
        template.convertAndSend("/subscribe/chat/room/inout/" + message.getChatroomId(), message);

    }

    @MessageMapping("/chat/talk")
    public void talk(ChatMessageDto message) {
        template.convertAndSend("/subscribe/chat/room/" + message.getChatroomId(), message);
    }

    @MessageMapping("/chat/exit")
    public void exit(ChatMessageDto message) {
        message.setMessage(message.getSenderId() + "님이 퇴장하셨습니다.");
        template.convertAndSend("/subscribe/chat/room/inout/" + message.getChatroomId(), message);
    }
}
