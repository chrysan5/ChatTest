package com.example.chatTest.service;

import com.example.chatTest.dto.ChatMessageDto;
import com.example.chatTest.model.ChatMessage;
import com.example.chatTest.model.Chatroom;
import com.example.chatTest.model.MessageType;
import com.example.chatTest.repository.ChatMessageRepository;
import com.example.chatTest.repository.ChatroomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Service
public class ChatMessageService {

    private final ChatroomRepository chatroomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public void saveChatMessage(ChatMessageDto chatMessageDto) {
        Chatroom chatroom = chatroomRepository.findById(chatMessageDto.getChatroomId()).orElseThrow(
                () -> new RuntimeException("채팅방이 없습니다")
        );

        chatMessageRepository.save(new ChatMessage(chatMessageDto, chatroom));
    }

    @Transactional
    public void saveChatMessage(ChatMessageDto chatMessageDto, String enterMsg) {
        Chatroom chatroom = chatroomRepository.findById(chatMessageDto.getChatroomId()).orElseThrow(
                () -> new RuntimeException("채팅방이 없습니다")
        );

        chatMessageRepository.save(new ChatMessage(chatMessageDto, enterMsg, chatroom));
    }

    public String getChatMessages(ChatMessageDto message) {
        Chatroom chatroom = chatroomRepository.findById(message.getChatroomId()).orElseThrow(
                () -> new RuntimeException("채팅방이 없습니다")
        );

        List<ChatMessage> chatMessageList = chatMessageRepository.findAllByChatroom(chatroom);

        String beforeMsgs = ""; //채팅방 과거 메시지 기록을 저장할 String
        for(ChatMessage chatMessage : chatMessageList) {
            //시간 포맷 설정
            LocalDateTime time = chatMessage.getSendTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. MM. dd. a h:mm:ss");
            String formattedDateTime = time.format(formatter);

            if (chatMessage.getType() == MessageType.CHAT) {
                beforeMsgs += "[" + chatMessage.getSenderId() + "] " + chatMessage.getMessage() + " (" + formattedDateTime + ")" + "<br>";
            } else { //enter, exit의 경우
                beforeMsgs += chatMessage.getMessage() + " (" + formattedDateTime + ")" + "<br>";
            }
        }

        return beforeMsgs;
    }
}
