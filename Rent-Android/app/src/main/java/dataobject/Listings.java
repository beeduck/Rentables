package dataobject;

import java.util.ArrayList;
import java.util.HashMap;

public class Listings {

    private String keywords = null;
    private String minPrice = null;
    private String maxPrice = null;
    private String priceCategoryId = null;
    private int userId = -1;
    private ArrayList<Listing> listings = new ArrayList<>();

    public Listings(){


    }

    public Listings(String newKeywords, String newMinPrice, String newMaxPrice, String newPriceCategoryId){

        setKeywords(newKeywords);
        setMinPrice(newMinPrice);
        setMaxPrice(newMaxPrice);
        setPriceCategoryId(newPriceCategoryId);

    }

    public Listings(String newKeywords, String newMinPrice, String newMaxPrice, String newPriceCategoryId, int newUserId){

        setKeywords(newKeywords);
        setMinPrice(newMinPrice);
        setMaxPrice(newMaxPrice);
        setPriceCategoryId(newPriceCategoryId);
        setUserId(newUserId);

    }

    public void setListings(ArrayList<Listing> currentListings){

        listings = currentListings;
    }

    public ArrayList<Listing> getListings(){

        return listings;
    }

    public void setKeywords(String newKeywords){

        keywords = newKeywords;
    }

    public String getKeywords(){

        return keywords;
    }

    public void setMinPrice(String newMinPrice){

        if(newMinPrice.length() == 0){

            minPrice = null;
        }else{

            double min = Double.parseDouble(newMinPrice);

            if(min >= 0){

                minPrice = String.valueOf(min);

            }else {

                minPrice = null;

            }
        }
    }

    public String getMinPrice(){

        return minPrice;
    }

    public void setMaxPrice(String newMaxPrice){

        if(newMaxPrice.length() == 0){

            maxPrice = null;
        }else{

            double max = Double.parseDouble(newMaxPrice);

            if(max > 0){

                maxPrice = String.valueOf(max);
            }else{

                maxPrice = null;
            }
        }
    }

    public String getMaxPrice(){

        return maxPrice;
    }

    public void setPriceCategoryId(String newPriceCategoryId){

        if(newPriceCategoryId.length() != 0){

            int rep = Integer.parseInt(newPriceCategoryId);

            if(rep >= 1 && rep <= 4){

                priceCategoryId = String.valueOf(rep);

            }else {

                priceCategoryId = "0";
            }
        }
    }

    public String getPriceCategoryId(){

        return priceCategoryId;
    }

    public void setUserId(int newUserId){

        if(newUserId >= 0){

            userId = newUserId;
        }else{

            userId = 0;
            throw new RuntimeException("ERROR: userId in listings has been set to an invalid value!");
        }
    }

    public int getUserId(){

        return userId;
    }

    public HashMap<String, String> getAllFields(){

        HashMap<String, String> theFields = new HashMap<String, String>();

        if(this.getKeywords() != null){

            theFields.put("keywords", this.getKeywords());
        }

        if(this.getMinPrice() != null){

            theFields.put("minPrice", this.getMinPrice());
        }

        if(this.getMaxPrice() != null){

            theFields.put("maxPrice", this.getMaxPrice());
        }

        if(this.getPriceCategoryId() != null){

            theFields.put("priceCategoryId", this.getPriceCategoryId());
        }

        if(this.getUserId() != -1){

            theFields.put("userId", String.valueOf(this.getUserId()));
        }

        return theFields;
    }
}
