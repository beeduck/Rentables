package com.rent.chat.services.messages;

import com.rent.chat.entities.Message;
import com.rent.chat.services.session.SessionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

/**
 * Created by duck on 3/16/17.
 */
@Component
public class MessageHandlerServiceImpl implements MessageHandlerService {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private SessionsService sessionsService;

    public void handleMessage(Message message, int userId) {
        if(!sessionsService.findSession(userId))
            System.out.println("User not connected.");
            // TODO: Send notification to user of new message
        else
            sendMessage(message, userId);

        // TODO: Append message to this chat
    }

    private void sendMessage(Message message, int userId) {
        messagingTemplate.convertAndSend( "/topic/" + userId, message);
    }

}
