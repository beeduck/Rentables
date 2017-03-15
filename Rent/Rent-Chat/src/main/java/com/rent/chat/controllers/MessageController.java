package com.rent.chat.controllers;

import com.rent.chat.entities.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by duck on 2/22/17.
 */
@Controller
public class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/test")
    public Greeting greeting() {
//        System.out.println(message.toString());
        return new Greeting("Hello!");
    }

    @MessageMapping("/hello/{user-id}")
//    @SendTo("/topic/test")
    @SendTo("/topic/{user-id}")
    public Message response(Message message, @DestinationVariable("user-id") String username) {
//        return new Greeting("Mr: " + username);
        return message;
    }

    @SendTo("/topic/{user-id}")
    public Greeting response() {
        return new Greeting("test");
    }
}

class Greeting {

    private String content;

    public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}