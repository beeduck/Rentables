package com.rent.chat.services.listeners;

import com.rent.chat.services.session.SessionsService;
import com.rent.utility.oauth.CustomOAuth2Authentication;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

/**
 * Created by duck on 3/16/17.
 */
@Component
class StompConnectedEvent implements ApplicationListener<SessionConnectedEvent> {

    private final Log logger = LogFactory.getLog(StompConnectedEvent.class);

    @Autowired
    private SessionsService sessionsService;

    public void onApplicationEvent(SessionConnectedEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());

//        String  company = sha.getNativeHeader("company").get(0);
//        logger.debug("Connect event [sessionId: " + sha.getSessionId() +"; company: "+ company + " ]");

        CustomOAuth2Authentication principal = (CustomOAuth2Authentication) event.getUser();

        sessionsService.addSession(principal.getUserId());
        System.out.println("Wait");
    }
}