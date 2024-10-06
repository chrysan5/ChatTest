package com.example.chatTest.controller;


import com.example.chatTest.repository.ChatroomRepository;
import com.example.chatTest.model.Chatroom;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.concurrent.atomic.AtomicInteger;


@AllArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatroomRepository chatroomRepository;
    private final AtomicInteger seq = new AtomicInteger(0); //이거 없애고 진짜 유저를 넣어야 한다.

    //채팅룸 목록
    @GetMapping("/rooms")
    public String rooms(Model model) {
        model.addAttribute("rooms", chatroomRepository.findAll());
        return "/rooms";
    }

    //채팅방 들어가기(상세)
    @GetMapping("/rooms/{chatroomId}")
    public String room(@PathVariable String chatroomId, Model model) {
        Chatroom room = chatroomRepository.findById(Long.valueOf(chatroomId)).orElseThrow(
                () -> new RuntimeException("방 존재하지 않습니다.")
        );
        model.addAttribute("room", room);
        model.addAttribute("member", "member" + seq.incrementAndGet());
        return "/room-detail";
    }

    //채팅방 나가기
    @GetMapping("/rooms-out")
    public String main(Model model) {
        model.addAttribute("rooms", chatroomRepository.findAll());
        return "redirect:/chat/rooms";
    }
}
