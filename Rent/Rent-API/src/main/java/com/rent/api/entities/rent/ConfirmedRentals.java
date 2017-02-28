package com.rent.api.entities.rent;

import com.rent.utility.DateUtils;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by duck on 2/28/17.
 */

@Entity
@Table(name = "ConfirmedRentals")
public class ConfirmedRentals {
    private int id;
    private int listingId;
    private int rentingUser;
    private Timestamp createDate;

    public ConfirmedRentals() { }

    public ConfirmedRentals(int listingId, int rentingUser) {
        this.listingId = listingId;
        this.rentingUser = rentingUser;
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
    @Column(name = "rentingUser")
    public int getRentingUser() {
        return rentingUser;
    }

    public void setRentingUser(int rentingUser) {
        this.rentingUser = rentingUser;
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
