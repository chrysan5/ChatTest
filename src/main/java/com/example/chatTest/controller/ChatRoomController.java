package com.example.chatTest.controller;


import com.example.chatTest.model.Chatroom;
import com.example.chatTest.repository.ChatroomRepository;
import com.example.chatTest.springSecurity.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@AllArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatroomRepository chatroomRepository;

    //채팅룸 목록
    @GetMapping("/rooms")
    public String rooms(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("rooms", chatroomRepository.findAll());
        model.addAttribute("username", userDetails.getUsername());
        return "/rooms";
    }

    //채팅방 들어가기(상세)
    @GetMapping("/rooms/{chatroomId}")
    public String room(@PathVariable String chatroomId, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) { //이거 왜 안되지
        Chatroom room = chatroomRepository.findById(Long.valueOf(chatroomId)).orElseThrow(
                () -> new RuntimeException("방 존재하지 않습니다.")
        );

        model.addAttribute("room", room);
        model.addAttribute("username", userDetails.getUsername());
        return "/room-detail";
    }

    //채팅방 나가기
    @GetMapping("/rooms-out")
    public String main(Model model) {
        model.addAttribute("rooms", chatroomRepository.findAll());
        return "redirect:/chat/rooms";
    }
}
