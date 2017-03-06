package com.rent.api.entities.rent;

import com.rent.utility.DateUtils;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by duck on 3/2/17.
 */
@Entity
@Table(name = "PendingReturn")
public class PendingReturn {
    private int id;
    private int listingId;
    private int returningUserId;
    private Timestamp createDate;

    public PendingReturn() { }

    public PendingReturn(int listingId, int returningUserId) {
        this.listingId = listingId;
        this.returningUserId = returningUserId;
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
    @Column(name = "listingId")
    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    @Basic
    @Column(name = "returningUserId")
    public int getReturningUserId() {
        return returningUserId;
    }

    public void setReturningUserId(int returningUserId) {
        this.returningUserId = returningUserId;
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
