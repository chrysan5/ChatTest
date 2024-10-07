package com.example.chatTest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

   /* private final AuthChannelInterceptor channelInterceptor;

    public WebSocketConfig(AuthChannelInterceptor channelInterceptor) {
        this.channelInterceptor = channelInterceptor;
    }
*/

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/publish");
        registry.enableSimpleBroker("/subscribe");
    }

   /* @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // Add our interceptor for authentication/authorization
        registration.interceptors(channelInterceptor);
    }*/
}
