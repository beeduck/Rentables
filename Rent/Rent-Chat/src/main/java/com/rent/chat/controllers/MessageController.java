package com.rent.chat.controllers;

import com.rent.chat.entities.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by duck on 2/22/17.
 */
@Controller
public class MessageController {

    @MessageMapping("/hello/{user-id}")
    @SendTo("/topic/{user-id}")
    public Message response(Message message, @DestinationVariable("user-id") String username) {
        return message;
    }

}
