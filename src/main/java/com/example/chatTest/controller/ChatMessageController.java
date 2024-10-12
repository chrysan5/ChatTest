package com.example.chatTest.controller;

import com.example.chatTest.dto.ChatMessageDto;
import com.example.chatTest.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatMessageController {

    private final SimpMessagingTemplate template;
    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(SimpMessagingTemplate template, ChatMessageService chatMessageService) {
        this.template = template;
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chat/enter")
    public void enter(ChatMessageDto message){
        //채팅방 과거 메시지 기록 조회
        String beforeMsgs = chatMessageService.getChatMessages(message);

        String enterMsg = message.getSenderId() + "님이 입장하셨습니다.";
        message.setMessage(beforeMsgs + enterMsg);

        chatMessageService.saveChatMessage(message, enterMsg);
        template.convertAndSend("/subscribe/chat/room/inout/" + message.getChatroomId(), message);

    }

    @MessageMapping("/chat/talk")
    public void talk(ChatMessageDto message) {
        chatMessageService.saveChatMessage(message);
        template.convertAndSend("/subscribe/chat/room/" + message.getChatroomId(), message);
    }

    @MessageMapping("/chat/exit")
    public void exit(ChatMessageDto message) {
        message.setMessage(message.getSenderId() + "님이 퇴장하셨습니다.");
        chatMessageService.saveChatMessage(message);
        template.convertAndSend("/subscribe/chat/room/inout/" + message.getChatroomId(), message);
    }
}
