package com.rent.data.dataaccess.api.entities.user;

import javax.persistence.*;

/**
 * Created by Duck on 11/11/2016.
 */
@Entity
@Table(name = "UserInfo")
public class UserInfo {

    private int id;
    private String username;

    public UserInfo() { }

    public UserInfo(int userId, String username) {
        this.id = userId;
        this.username = username;
    }

    @Id
    @Basic
    @Column(name = "userId", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
