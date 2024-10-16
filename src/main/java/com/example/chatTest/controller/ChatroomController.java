package com.example.chatTest.controller;


import com.example.chatTest.model.Chatroom;
import com.example.chatTest.rabbitMQ.AuctionInfoMessage;
import com.example.chatTest.service.ChatroomService;
import com.example.chatTest.springSecurity.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatroomController {

    private final ChatroomService chatroomService;

    //채팅방 목록
    @GetMapping("/rooms-list")
    public String getChatroomList(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username", userDetails.getUsername());

        //모든 채팅방 목록
        List<Chatroom> chatroomList = chatroomService.getChatroomList();
        model.addAttribute("rooms", chatroomList);

        //내가 속한 채팅방 목록
        List<Chatroom> myChatroomList = chatroomService.getMyChatroomList(userDetails.getUser().getUserId());
        model.addAttribute("myrooms", myChatroomList);

        return "/rooms";
    }


    //채팅방 들어가기(상세)
    @GetMapping("/rooms/{chatroomId}")
    public String enterChatroom(@PathVariable String chatroomId, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Chatroom chatroom = chatroomService.enterChatroom(chatroomId, userDetails.getUser().getUserId());
        model.addAttribute("room", chatroom);
        model.addAttribute("username", userDetails.getUsername());
        return "/room-detail";
    }

    //채팅방 나가기 -> 채팅방을 나가도 채팅방을 삭제하지 않으면 과거 채팅 기록 조회 가능함
    @GetMapping("/rooms-out")
    public String exitChatroom(Model model) {
        List<Chatroom> chatroomList = chatroomService.getChatroomList();
        model.addAttribute("rooms", chatroomList);
        return "redirect:/chat/rooms-list";
    }

    //채팅방 생성
    @PostMapping("/rooms")
    public String createChatroom(
            @RequestParam("roomname") String roomname,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        chatroomService.createChatroom(roomname, userDetails.getUser().getUserId());
        return "redirect:/chat/rooms-list";
    }

    //rabbitmq 메시징시스템에 의한 채팅방 생성
    @RabbitListener(queues = "${message.queue.auction-info}")
    public void receiveAuctionInfoMessage(AuctionInfoMessage auctionInfoMessage) {
        log.info("CHAT RECEIVE:{}", auctionInfoMessage.toString());
        chatroomService.createChatroom(auctionInfoMessage);
    }
    
    //채팅방 삭제 -> 채팅방 목록에서 보이지 않으므로 과거 채팅 기록 조회 불가능
    @PutMapping("/rooms/{chatroomId}")
    public String deleteChatroom(@PathVariable String chatroomId){
        chatroomService.deleteChatMember(chatroomId);
        return "redirect:/chat/rooms-list";
    }
}
