package com.example.jwtandwebsocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/noauth/wsMessage")
public class SendWsMessageController extends BaseController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/{id}")
    public ResponseEntity<?> sendMessageToSpecificUser(@PathVariable(name = "id") UUID id, @RequestBody Object payload) {
        messagingTemplate.convertAndSend("/topic/user/" + id, payload);
        return ResponseEntity.ok("Send message success");
    }

}
