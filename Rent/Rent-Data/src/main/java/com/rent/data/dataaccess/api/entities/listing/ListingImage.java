package com.rent.data.dataaccess.api.entities.listing;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Asad on 11/28/2016.
 */

@Entity
@Table(name = "ListingImages")
public class ListingImage implements Serializable {
    private int id;
    private String path;
    private int listingId;
    private String imageUUID;

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
    @Column(name = "path", nullable = false)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
    @Column(name = "imageUUID")
    public String getImageUUID() {
        return imageUUID;
    }

    public void setImageUUID(String imageUUID) {
        this.imageUUID = imageUUID;
    }
}
