package Utilities.Filters;

import javax.validation.Valid;

/**
 * Created by Asad on 11/9/2016.
 */
public class UserPostFilter {

    @Valid
    private String[] keywords = null;

    @Valid
    private int userId;

    @Valid
    private int priceCategoryId;

    @Valid
    private double maxPrice;

    @Valid
    private double minPrice;

    @Valid
    private int id;

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPriceCategoryId() {
        return priceCategoryId;
    }

    public void setPriceCategoryId(int priceCategoryId) {
        this.priceCategoryId = priceCategoryId;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
