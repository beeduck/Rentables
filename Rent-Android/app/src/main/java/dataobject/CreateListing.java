package dataobject;

public class CreateListing {

    private String title;
    private String description;
    private double price;
    private int priceCategoryId;
    private int userId;

    public void setTitle(String newTitle){

        title = newTitle;
    }

    public String getTitle(){

        return title;
    }

    public void setDescription(String newDescription){

        description = newDescription;
    }

    public String getDescription(){

        return description;
    }

    public void setPrice(double newPrice){

        if(newPrice >= 0){

            price = newPrice;
        }
    }

    public double getPrice(){

        return price;
    }

    public void setPriceCategoryId(int newPriceCategoryId){

        if(newPriceCategoryId >= 1 && newPriceCategoryId <= 5){

            priceCategoryId = newPriceCategoryId;
        }
    }

    public int getPriceCategoryId(){

        return priceCategoryId;
    }

    public void setUserId(int newUserId){

        userId = newUserId;
    }

    public int getUserId(){

        return userId;
    }
}
