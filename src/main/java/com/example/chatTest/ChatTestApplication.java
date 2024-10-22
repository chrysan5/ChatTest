package com.example.chatTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class ChatTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatTestApplication.class, args);
	}

	@Controller
	public static class MainController {
		@GetMapping("/")
		public String main() {
			return "login";
		}
	}
}
