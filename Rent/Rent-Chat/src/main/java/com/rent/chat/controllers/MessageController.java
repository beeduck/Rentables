package com.rent.chat.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

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