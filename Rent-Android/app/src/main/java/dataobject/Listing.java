package dataobject;

import java.util.List;

public class Listing {

    private int id;
    private String title;
    private String description;
    private String createDate;
    private String lastEditDate;
    private String userId;
    private String priceCategoryId;
    private String[] images;
    private double price;
    private boolean active;

    private List<RentRequest> rentRequests;

    public void setId(int i){id = i;}
    public void setTitle(String s){title = s;}
    public void setDescription(String s){description = s;}
    public void setCreateDate(String s){createDate = s;}
    public void setLastEditDate(String s){lastEditDate = s;}
    public void setUserId(String s){userId = s;}
    public void setPriceCategoryId(String s){priceCategoryId = s;}
    public void setImages(String[] i){images = i;}
    public void setPrice(double d){price = d;}
    public void setActive(boolean b){active = b;}
    public void setRentRequests(List<RentRequest> rentRequests) {
        this.rentRequests = rentRequests;
    }

    public int getId(){return id;}
    public String getTitle(){return title;}
    public String getDescription(){return description;}
    public String getCreateDate(){return createDate;}
    public String getLastEditDate(){return lastEditDate;}
    public String getUserId(){return userId;}
    public String getPriceCategoryId(){return priceCategoryId;}
    public String[] getImages(){return images;}
    public double getPrice(){return price;}
    public boolean getActive(){return active;}
    public List<RentRequest> getRentRequests() {
        return rentRequests;
    }
}
