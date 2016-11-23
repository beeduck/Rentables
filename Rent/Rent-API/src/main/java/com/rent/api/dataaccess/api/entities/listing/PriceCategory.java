package com.rent.api.dataaccess.api.entities.listing;

import javax.persistence.*;

/**
 * Created by Asad on 11/4/2016.
 */

@Entity
@Table(name = "PriceCategories")
public class PriceCategory {
    private int id;
    private String category;

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
    @Column(name = "category", nullable = false)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
