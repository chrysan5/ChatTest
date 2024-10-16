package com.example.chatTest.rabbitmqtest;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ChatApplicationQueueConfig {

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Value("${message.exchange}")
    private String exchange;

    @Value("${message.queue.auction-info}")
    private String queueAuctionInfo;

   /* @Value("${message.err-exchange}")
    private String exchangeErr;

    @Value("${message.err-queue.auction}")
    private String queueErrAuction;*/



    //exchage 생성 코드----------------------------------
    @Bean public TopicExchange exchange() { return new TopicExchange(exchange); }

    //큐 생성 코드
    @Bean public Queue queueAuctionInfo() { return new Queue(queueAuctionInfo); }

    //바인딩 생성 코드
    @Bean public Binding bindingAuctionInfo() { return BindingBuilder.bind(queueAuctionInfo()).to(exchange()).with(queueAuctionInfo); }



    //에러 exchage 생성 코드---------------------------------
/*    @Bean public TopicExchange exchangeErr() { return new TopicExchange(exchangeErr); }

    //에러 큐 생성 코드
    @Bean public Queue queueErrAuction() { return new Queue(queueErrAuction); }

    //에러 바인딩 생성 코드
    @Bean public Binding bindingErrAuction() { return BindingBuilder.bind(queueErrAuction()).to(exchangeErr()).with(queueErrAuction); }*/
}
