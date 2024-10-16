package com.example.chatTest.rabbitmqtest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//테스트용 - rabbitmq 프로듀서
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuctionEndpoint {

    @Value("${message.queue.auction-info}")
    private String actionInfoQueue;

    private final RabbitTemplate rabbitTemplate;


    @PostMapping("/rabbitmq")
    public ResponseEntity<Void> auction(@RequestBody AuctionInfoMessage auctionInfoMessage) {
        //auction 객체 저장 로직 수행
        rabbitTemplate.convertAndSend(actionInfoQueue, auctionInfoMessage); //레빗mq를 통한 메시지 전송***
        log.info("auction send Message : {}",auctionInfoMessage.toString());
        return ResponseEntity.noContent().build();
    }

/*    @RabbitListener(queues = "${message.err-queue.auction}")
    public void errAuction(DeliveryMessage message) {
        log.info("auction ERROR RECEIVE !!!" + message.toString());
        //acution 취소 로직 수행
    }*/
}

