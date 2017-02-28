package com.rent.api.entities.listing;

import com.rent.utility.DateUtils;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;


/**
 * Created by Asad on 10/31/2016.
 *
 */

@Entity
@Table(name = "UserPosts")
public class Listing implements Serializable {
    private int id;
    private String title;
    private String description;
    private boolean isActive;
    private Timestamp createDate;
    private Timestamp lastEditDate;
    private int userId;
    private int priceCategoryId;
    private double price;
    private List<String> images;

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
    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "isActive")
    @Type(type = "yes_no")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Basic
    @Column(name = "createDate")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "lastEditDate")
    public Timestamp getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(Timestamp lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    @Basic
    @Column(name = "userId")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "priceCategoryId")
    public int getPriceCategoryId() {
        return priceCategoryId;
    }

    public void setPriceCategoryId(int priceCategoryId) {
        this.priceCategoryId = priceCategoryId;
    }

    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @ElementCollection
    @CollectionTable(name="ListingImages", joinColumns=@JoinColumn(name="listingId"))
    @Column(name="ImageUUID")
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @PrePersist
    public void prePersist() {
        Timestamp currentTimestamp = DateUtils.getCurrentUtcTimestamp();

        this.lastEditDate = currentTimestamp;
        this.createDate = currentTimestamp;
    }

    @PreUpdate
    public void preUpdate() {
        this.lastEditDate = DateUtils.getCurrentUtcTimestamp();
    }
}

