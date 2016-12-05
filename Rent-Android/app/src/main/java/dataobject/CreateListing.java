package dataobject;

public class CreateListing {

    private String title;
    private String description;
    private String priceCategoryId;
    private String price;
    private String userId;

    public void setTitle(String newTitle){

        title = newTitle;
    }

    public void setDescription(String newDescription){

        description = newDescription;
    }

    public void setPriceCategoryId(String newPriceCategoryId){

        priceCategoryId = newPriceCategoryId;
    }

    public void setPrice(String newPrice){

        price = newPrice;
    }

    public void setUserId(String newUserId){

        userId = newUserId;
    }
}
