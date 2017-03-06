package com.rent.api.entities.rent;

import com.rent.utility.DateUtils;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by duck on 2/28/17.
 */
@Entity
@Table(name = "RentRequests")
public class RentRequests {
    private int id;
    private int listingId;
    private int requestingUser;
    private boolean isAccepted;
    private Timestamp createDate;

    public RentRequests() { }

    public RentRequests(int listingId, int requestingUser) {
        this.listingId = listingId;
        this.requestingUser = requestingUser;
        this.isAccepted = false;
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
    @Column(name = "listingId", nullable = false)
    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    @Basic
    @Column(name = "requestingUser", nullable = false)
    public int getRequestingUser() {
        return requestingUser;
    }

    public void setRequestingUser(int requestingUser) {
        this.requestingUser = requestingUser;
    }

    @Basic
    @Column(name = "isAccepted")
    @Type(type = "yes_no")
    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    @Basic
    @Column(name = "createDate")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @PrePersist
    public void prePersist() {
        this.createDate = DateUtils.getCurrentUtcTimestamp();
    }
}
