package dataobject;

public class Listing {

    private int id;
    private String title;
    private String description;
    private String createDate;
    private String lastEditDate;
    private String userId;
    private String priceCategoryId;
    private double price;
    private boolean active;

    public int getId(){

        return id;
    }

    public String getTitle(){

        return title;
    }

    public String getDescription(){

        return description;
    }

    public String getCreateDate(){

        return createDate;
    }

    public String getLastEditDate(){

        return lastEditDate;
    }

    public String getUserId(){

        return userId;
    }

    public String getPriceCategoryId(){

        return priceCategoryId;
    }

    public double getPrice(){

        return price;
    }

    public boolean getActive(){

        return active;
    }

    public void printProperties(){

        System.out.println("Properties of Listing: " + this.getId());
        System.out.println("Title: " + this.getTitle());
        System.out.println("Description: " + this.getDescription());
        System.out.println("Created: " + this.getCreateDate());
        System.out.println("Last Edit Date: " + this.getLastEditDate());
        System.out.println("User: " + this.getUserId());
        System.out.println("Price: " + this.getPrice());
        System.out.println("Active: " + this.getActive());
        System.out.println(" ");
        System.out.println(" ");

    }
}
