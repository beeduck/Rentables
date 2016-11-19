package com.rent.utility.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by Duck on 11/20/2016.
 */
public class NewUserDTO {

    @NotNull
    private String username;

    @NotNull
    private int userId;

    public NewUserDTO() {

    }

    public NewUserDTO(String username, int userId) {
        this.username = username;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
