package com.rent.chat.services.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by duck on 3/16/17.
 */
@Component
public class SessionsServiceImpl implements SessionsService {
    private final Logger logger = LoggerFactory.getLogger(SessionsService.class);

    private static HashMap<Integer, Boolean> sessions = new HashMap<Integer, Boolean>();

    public void addSession(int userId) {
        logger.info("Session added, user ID:" + userId);
        sessions.put(userId, true);
    }

    public void removeSession(int userId) {
        logger.info("Session removed, user ID:" + userId);
        sessions.remove(userId);
    }

    public boolean findSession(int userId) {
        return sessions.containsKey(userId);
    }
}
