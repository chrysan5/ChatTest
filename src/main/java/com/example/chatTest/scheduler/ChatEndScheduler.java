package com.example.chatTest.scheduler;

import org.springframework.transaction.annotation.Transactional;
import com.example.chatTest.model.Chatroom;
import com.example.chatTest.repository.ChatroomRepository;
import com.example.chatTest.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Component
public class ChatEndScheduler {
    private final ChatroomRepository chatroomRepository;
    private final ChatroomService chatroomService;

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    @Transactional
    public void chatEnd() {
        List<Chatroom> chatroomList =  chatroomRepository.findAllByAuctionEndTimeIsNotNull();

        for(Chatroom chatroom : chatroomList){
            LocalDateTime auctionEndTime = LocalDateTime.parse(chatroom.getAuctionEndTime());

            if(auctionEndTime.isBefore(LocalDateTime.now())){ //auctionEndTime이 현재시간보다 이전시간이면
                chatroomService.deleteChatMemberAll(chatroom.getChatroomId());
            }

            chatroom.setDelete(true);
        }
    }
}