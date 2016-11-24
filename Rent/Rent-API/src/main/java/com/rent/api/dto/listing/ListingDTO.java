package com.rent.api.dto.listing;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Asad on 11/1/2016.
 */
public class ListingDTO implements Serializable {

    @NotNull(message = "Title required")
    private String title;

    private String description;

    // TODO: Implement custom validator for price category ID based on DB options
    @NotNull(message = "Payment interval required")
    private Integer priceCategoryId;

    @NotNull(message = "Price required")
    private Double price;

    public ListingDTO(){}

    public ListingDTO(String title, String description, int priceCategoryId, double price) {
        this.title = title;
        this.description = description;
        this.priceCategoryId = priceCategoryId;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriceCategoryId() {
        return priceCategoryId;
    }

    public void setPriceCategoryId(int priceCategoryId) {
        this.priceCategoryId = priceCategoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
