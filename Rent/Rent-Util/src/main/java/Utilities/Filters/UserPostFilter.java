package Utilities.Filters;

/**
 * Created by Asad on 11/9/2016.
 */
public class UserPostFilter {

    private String[] keywords = null;
    private int userId;
    private int priceCategoryId;
    private double maxPrice;
    private double minPrice;

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
}
