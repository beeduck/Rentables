package dataobject;

public class CreateListing {

    private String title;
    private String description;
    private double price;
    private int priceCategoryId;
    private int userId;
    private ListingImage listingImage = new ListingImage();

    public void setTitle(String newTitle){title = newTitle;}
    public void setDescription(String newDescription){description = newDescription;}

    public String getTitle(){return title;}
    public String getDescription(){return description;}
    public double getPrice(){return price;}
    public int getPriceCategoryId(){return priceCategoryId;}

    public void setPrice(double newPrice){

        if(newPrice >= 0){

            price = newPrice;
        }
    }

    public void setPriceCategoryId(int newPriceCategoryId){

        if(newPriceCategoryId >= 1 && newPriceCategoryId <= 5){

            priceCategoryId = newPriceCategoryId;
        }
    }

    public void setUserId(int newUserId){

        userId = newUserId;
    }

    public int getUserId(){return userId;}
    public ListingImage getListingImage(){return listingImage;}
}
