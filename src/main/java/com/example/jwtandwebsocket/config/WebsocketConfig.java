//package com.example.jwtandwebsocket.config;
//
//import com.example.jwtandwebsocket.service.websocket.handler.MyWsHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
//
//@Configuration
//@EnableWebSocket
//public class WebsocketConfig implements WebSocketConfigurer {
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry
//                .addHandler(myHandler(), "/myHandler")
//                .setAllowedOrigins("*")
//                .withSockJS();
////        registry.addHandler(myHandler())
////        .addInterceptors(new HttpSessionHandshakeInterceptor()); // before and after hanshake interceptor
//    }
//
//    @Bean
//    public WebSocketHandler myHandler() {
//        return new MyWsHandler();
//    }
//
//    @Bean
//    public ServletServerContainerFactoryBean createWebSocketContainer() {
//        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
//        container.setMaxTextMessageBufferSize(8192);
//        container.setMaxBinaryMessageBufferSize(8192);
//        return container;
//    }
//
//}
