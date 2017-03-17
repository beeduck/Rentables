package com.rent.chat.services.session;

/**
 * Created by duck on 3/16/17.
 */
public interface SessionsService {
    void addSession(int userId);
    void removeSession(int userId);
    boolean findSession(int userId);
}
