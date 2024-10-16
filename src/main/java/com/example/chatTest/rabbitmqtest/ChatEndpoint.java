package com.example.chatTest.rabbitmqtest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//테스트용 - rabbitmq 컨슈머
@Slf4j
@Component
@RequiredArgsConstructor
public class ChatEndpoint {

    /*@Value("${message.err-queue.auction}")
    private String AuctionErrorQueue;

    private final RabbitTemplate rabbitTemplate;*/


    @RabbitListener(queues = "${message.queue.auction-info}")
    public void receiveMessage(AuctionInfoMessage auctionInfoMessage) {
        //deliveryMessage에서 아이디와 정보를 찾아서 로직을 수행

        /*if (false) { //이상 조건시
            this.rollbackChat(deliveryMessage);
            return;
        }*/

        log.info("CHAT RECEIVE:{}", auctionInfoMessage.toString());
    }

    /*public void rollbackChat(DeliveryMessage deliveryMessage){
        log.info("CHAT ROLLBACK!!!");
        //로직 수행
        rabbitTemplate.convertAndSend(AuctionErrorQueue, deliveryMessage);
    }*/
}