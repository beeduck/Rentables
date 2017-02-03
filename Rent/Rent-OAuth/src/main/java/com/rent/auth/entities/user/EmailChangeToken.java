package com.rent.auth.entities.user;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Duck on 2/3/2017.
 */

@Entity
@Table(name = "emailChangeTokens")
public class EmailChangeToken {

    private int id;

    private String token;

    private Date expirationDate;

    private int userId;

    private String newEmail;

    public EmailChangeToken() { }

    public EmailChangeToken(String token, int userId, Date expirationDate, String newEmail) {
        this.userId = userId;
        this.token = token;
        this.expirationDate = expirationDate;
        this.newEmail = newEmail;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "token", nullable = false)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "expirationDate", nullable = false)
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Basic
    @Column(name = "userId", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "newEmail", nullable = false)
    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
