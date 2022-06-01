package com.example.jwtandwebsocket.controller;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class WebsocketController extends BaseWebsocketController {

    // handler all send request from client to path "/app/greeting",
    // the returned value being sent to broker channel, then being sent to topic "/topic/greeting"
    @MessageMapping("/greeting")
    public String sendGreetingMessage(String message,  Principal user,
                                      @Header("simpSessionId") String sessionId) {
        System.out.println("Getting some message: " + message);
        return "[" + LocalDate.now().toString() + ": " + message;
    }


}
