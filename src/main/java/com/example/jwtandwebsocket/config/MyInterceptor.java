package com.example.jwtandwebsocket.config;

import com.example.jwtandwebsocket.service.security.model.JwtAuthenticationToken;
import com.example.jwtandwebsocket.service.security.model.SecurityUser;
import com.example.jwtandwebsocket.utils.token.JwtTokenFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

//@Order(Ordered.HIGHEST_PRECEDENCE + 99)
//public class MyInterceptor implements ChannelInterceptor {
//
//    @Autowired
//    private JwtTokenFactory tokenFactory;
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//            String token = (String) accessor.getHeader("token"); // access authentication header(s)
//            SecurityUser securityUser = tokenFactory.parseAccessJwtToken(token);
//
//
//            Authentication authResult = new JwtAuthenticationToken(securityUser);
//
//            SecurityContext context = SecurityContextHolder.createEmptyContext();
//            context.setAuthentication(authResult);
//            SecurityContextHolder.setContext(context);
//
//            accessor.setUser(authResult);
//
//        }
//        return message;
//    }
//}
