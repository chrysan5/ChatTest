package com.example.chatTest.service;


import com.example.chatTest.model.Chatroom;
import com.example.chatTest.model.ChatroomMember;
import com.example.chatTest.rabbitMQ.AuctionInfoMessage;
import com.example.chatTest.repository.ChatroomMemberRepository;
import com.example.chatTest.repository.ChatroomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ChatroomService {
    private final ChatroomRepository chatroomRepository;
    private final ChatroomMemberRepository chatroomMemberRepository;


    @Transactional
    public Chatroom enterChatroom(String chatroomId, Long userId) {
        Chatroom chatroom =  chatroomRepository.findById(Long.valueOf(chatroomId)).orElseThrow(
                () -> new RuntimeException("채팅방이 존재하지 않습니다.")
        );

        //채팅룸 입장시 chatMember가 있다면 삭제상태를 false로 바꾸고, chatMember에 없다면 추가하기
        Optional<ChatroomMember> chatroomMember = chatroomMemberRepository.findByUserIdAndChatroom(userId, chatroom);
        if(chatroomMember.isPresent()){
            if(chatroomMember.get().isDelete() == true){
                chatroomMember.get().setDelete(false);
            }
        }else{
            chatroomMemberRepository.save(new ChatroomMember(chatroom, userId));
        }

        return chatroom;
    }

    public List<Chatroom> getChatroomList() {
        return chatroomRepository.findAll();
    }


    public List<Chatroom> getMyChatroomList(Long userId) {
        List<ChatroomMember> chatroomMemberList = chatroomMemberRepository.findAllByUserIdAndIsDeleteFalse(userId);
        List<Chatroom> chatroomList = new ArrayList<>();
        for (ChatroomMember chatroomMember : chatroomMemberList) {
            Chatroom chatroom = chatroomRepository.findById(chatroomMember.getChatroom().getChatroomId()).orElseThrow(
                    () -> new RuntimeException("채팅방이 없습니다")
            );
            chatroomList.add(chatroom);
        }
        return chatroomList;
    }

    @Transactional
    public void createChatroom(String roomname, Long userId) {
        Chatroom chatroom = chatroomRepository.save(new Chatroom(roomname));
        chatroomMemberRepository.save(new ChatroomMember(chatroom, userId));
    }

    @Transactional
    public void createChatroom(AuctionInfoMessage auctionInfoMessage) {
        String roomname = auctionInfoMessage.getTitle();
        String auctionEndTime = auctionInfoMessage.getEndTime();
        Long userId = auctionInfoMessage.getRegisterMemberUserId();

        Chatroom chatroom = chatroomRepository.save(new Chatroom(roomname, auctionEndTime));
        chatroomMemberRepository.save(new ChatroomMember(chatroom, userId));
    }

    @Transactional
    public void deleteChatMember(String chatroomId) {
        Chatroom chatroom = chatroomRepository.findById(Long.valueOf(chatroomId)).orElseThrow(
                () -> new RuntimeException("채팅방이 존재하지 않습니다.")
        );

        ChatroomMember chatroomMember = chatroomMemberRepository.findByChatroom(chatroom);
        chatroomMember.setDelete(true);
    }

    @Transactional
    public void deleteChatMemberAll(Long chatroomId) {
        Chatroom chatroom = chatroomRepository.findById(Long.valueOf(chatroomId)).orElseThrow(
                () -> new RuntimeException("채팅방이 존재하지 않습니다.")
        );

        List<ChatroomMember> chatroomMemberList = chatroomMemberRepository.findAllByChatroom(chatroom);
        for(ChatroomMember chatroomMember : chatroomMemberList){
            chatroomMember.setDelete(true);
        }
    }
}
