//package com.example.jwtandwebsocket.config;
//
//import com.example.jwtandwebsocket.common.constant.RespCode;
//import com.example.jwtandwebsocket.common.exception.MyValidationException;
//import com.example.jwtandwebsocket.service.security.model.JwtAuthenticationToken;
//import com.example.jwtandwebsocket.service.security.model.SecurityUser;
//import com.example.jwtandwebsocket.utils.token.JwtTokenFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.messaging.support.MessageHeaderAccessor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.util.List;
//
//@Slf4j
//@Order(Ordered.HIGHEST_PRECEDENCE + 99)
//public class MyInterceptor implements ChannelInterceptor {
//
//    @Autowired
//    private JwtTokenFactory tokenFactory;
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor =
//                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//            List<String> authorization = accessor.getNativeHeader("X-Authorization");
//            log.debug("X-Authorization: {}", authorization);
//
//            if (authorization.size() > 0) {
//                String accessToken = authorization.get(0);
//                SecurityUser securityUser = tokenFactory.parseAccessJwtToken(accessToken);
//
//                Authentication authentication = new JwtAuthenticationToken(securityUser);
//                accessor.setUser(authentication);
//
//                SecurityContext context = SecurityContextHolder.createEmptyContext();
//                context.setAuthentication(authentication);
//                SecurityContextHolder.setContext(context);
//            } else {
//                throw new MyValidationException("Invalid authorization header", RespCode.AUTHENTICATION);
//            }
//        }
//        return message;
//    }
//
//
//
//}
