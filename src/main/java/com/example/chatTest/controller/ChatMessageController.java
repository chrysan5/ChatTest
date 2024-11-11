package com.example.chatTest.controller;

import com.example.chatTest.dto.ChatMessageDto;
import com.example.chatTest.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
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

        String enterMsg = "--- " + message.getSenderId() + "님이 입장하셨습니다. ---";
        ChatMessageDto savedEnterMessage = chatMessageService.saveChatMessage(message, enterMsg);

        message.setMessage(beforeMsgs + savedEnterMessage.getMessage());
        //message.setMessage(beforeMsgs + enterMsg);

        template.convertAndSend("/subscribe/chat/room/inout/" + message.getChatroomId(), message);

    }

    @MessageMapping("/chat/talk")
    public void talk(ChatMessageDto message) {
        ChatMessageDto savedMessage = chatMessageService.saveChatMessage(message);
        template.convertAndSend("/subscribe/chat/room/" + message.getChatroomId(), savedMessage);
    }

    @MessageMapping("/chat/exit")
    public void exit(ChatMessageDto message) {
        message.setMessage("--- " + message.getSenderId() + "님이 퇴장하셨습니다. ---");
        chatMessageService.saveChatMessage(message);
        template.convertAndSend("/subscribe/chat/room/inout/" + message.getChatroomId(), message);
    }

}
