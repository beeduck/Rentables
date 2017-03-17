package com.rent.chat.controllers;

import com.rent.chat.entities.Message;
import com.rent.chat.services.messages.MessageHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

/**
 * Created by duck on 2/22/17.
 */
@Controller
public class MessageController {

    @Autowired
    private MessageHandlerService messageHandlerService;

    @MessageMapping("/hello/{user-id}")
//    @SendTo("/topic/{user-id}")
    public Message response(Message message, @DestinationVariable("user-id") int userId) {
        messageHandlerService.handleMessage(message, userId);
        return message;
    }
}
