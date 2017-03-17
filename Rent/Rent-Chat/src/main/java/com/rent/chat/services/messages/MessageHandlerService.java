package com.rent.chat.services.messages;

import com.rent.chat.entities.Message;

/**
 * Created by duck on 3/16/17.
 */
public interface MessageHandlerService {
    void handleMessage(Message message, int userId);
}
