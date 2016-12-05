package dataobject;

import java.util.HashMap;

public class Listings {

    private String keywords = null;
    private String minPrice = null;
    private String maxPrice = null;
    private String priceCategoryId = null;
    private static final int MAX_FIELDS = 4;

    public Listings(){


    }

    public Listings(String newKeywords, String newMinPrice, String newMaxPrice, String newPriceCategoryId){

        setKeywords(newKeywords);
        setMinPrice(newMinPrice);
        setMaxPrice(newMaxPrice);
        setPriceCategoryId(newPriceCategoryId);

    }

    public void setKeywords(String newKeywords){

        keywords = newKeywords;
    }

    public String getKeywords(){

        return keywords;
    }

    public void setMinPrice(String newMinPrice){

        int rep = Integer.parseInt(newMinPrice);

        if(rep >= 0){

            minPrice = String.valueOf(rep);

        }else {

            minPrice = "0";

        }
    }

    public String getMinPrice(){

        return minPrice;
    }

    public void setMaxPrice(String newMaxPrice){

        int rep = Integer.parseInt(newMaxPrice);

        if(rep >= 0){

            maxPrice = String.valueOf(rep);

        }else{

            maxPrice = "0";

        }
    }

    public String getMaxPrice(){

        return maxPrice;
    }

    public void setPriceCategoryId(String newPriceCategoryId){

        int rep = Integer.parseInt(newPriceCategoryId);

        if(rep >= 1 && rep <= 4){

            priceCategoryId = String.valueOf(rep);

        }else {

            priceCategoryId = "0";
        }
    }

    public String getPriceCategoryId(){

        return priceCategoryId;
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

        return theFields;
    }
}
