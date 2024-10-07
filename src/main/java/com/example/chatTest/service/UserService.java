package com.example.chatTest.service;


import com.example.chatTest.model.User;
import com.example.chatTest.repository.UserRepository;
import com.example.chatTest.dto.UserRequestDto;
import com.example.chatTest.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto signup(UserRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        User user = userRepository.save(new User(username, password));
        return new UserResponseDto(user);
    }

}
