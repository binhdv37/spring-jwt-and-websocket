package com.example.jwtandwebsocket.config;

import com.example.jwtandwebsocket.common.constant.RespCode;
import com.example.jwtandwebsocket.common.exception.MyValidationException;
import com.example.jwtandwebsocket.service.security.model.JwtAuthenticationToken;
import com.example.jwtandwebsocket.service.security.model.SecurityUser;
import com.example.jwtandwebsocket.utils.token.JwtTokenFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompReactorNettyCodec;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.tcp.reactor.ReactorNettyTcpClient;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99) // make interceptor highest precedence
public class WebsocketStompConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    JwtTokenFactory tokenFactory;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/ws") // ws establish endpoint
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    /*
        How to config rabbitmq:
            - Enable stomp support: rabbitmq-plugins enable rabbitmq_stomp
            - Map port: 61613 (rabbitmq stomp adapter default port), 15672, 5672
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        ReactorNettyTcpClient<byte[]> client = new ReactorNettyTcpClient<>(tcpClient -> tcpClient
                .host("localhost")
                .port(61613),
//                .secure(SslProvider.defaultClientProvider()),
                new StompReactorNettyCodec()
        );

        registry.setApplicationDestinationPrefixes("/app");
        registry.enableStompBrokerRelay("/topic")
                .setAutoStartup(true)
//                .setSystemLogin("amazonmq-login")
//                .setSystemPasscode("amazonmq-pass")
                .setClientLogin("guest")
                .setClientPasscode("guest")
                .setTcpClient(client);

//        registry.setApplicationDestinationPrefixes("/app");
//        registry.enableStompBrokerRelay("/topic")
//                .setRelayHost("localhost")
//                .setRelayPort(5672)
//                .setClientLogin("guest")
//                .setClientPasscode("guest");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new MyInterceptor());
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> authorization = accessor.getNativeHeader("X-Authorization");
                    log.debug("X-Authorization: {}", authorization);

                    if (authorization != null && authorization.size() > 0) {
                        String accessToken = authorization.get(0);
                        SecurityUser securityUser = tokenFactory.parseAccessJwtToken(accessToken);

                        Authentication authentication = new JwtAuthenticationToken(securityUser);
                        accessor.setUser(authentication); // set authen
                    } else {
                        // invalid token
                        throw new MyValidationException("Invalid authorization header", RespCode.AUTHENTICATION);
                    }
                }
                return message;
            }
        });
    }

}
