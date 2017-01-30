package com.rent.auth.entities.registration;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Duck on 10/28/2016.
 */
@Entity
@Table(name = "RegistrationTokens")
public class RegistrationToken {

    private int id;

    private String token;

    private Date expirationDate;

    private int userId;

    public RegistrationToken() { }

    public RegistrationToken(String token, int userId, Date expirationDate) {
        this.userId = userId;
        this.token = token;
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
    @Column(name = "expirationDate", nullable = false)
    public Date getExpirationDate() {

        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Basic
    @Column(name = "token", nullable = false)
    public String getToken() {

        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}
