package com.rent.chat.entities;

/**
 * Created by duck on 3/14/17.
 */
public class Message {

    private int userId;
    private String message;

    public Message() { }

    public Message(int userId, String message) {
        this.getUserId();
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
