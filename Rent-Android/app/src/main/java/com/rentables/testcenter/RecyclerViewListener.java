package com.rentables.testcenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.rentables.testcenter.activity.ListingDetailsActivity;

import java.util.ArrayList;

import dataobject.Listing;
import dataobject.RentRequest;

public class RecyclerViewListener implements View.OnClickListener {

    private ListingsAdapter listingsAdapter;
    private RecyclerView searchRecyclerView;
    private ArrayList<Listing> listings;
    private Context contextForListener;

    @Override
    public void onClick(View v) {

        //TODO This is where the listings detail activity will be started.

        int itemPosition = searchRecyclerView.getChildLayoutPosition(v);
        Listing currentListing = getCurrentListing(itemPosition);

        //Now that we have the listing that was clicked on we can start a
        //new intent to go to the activity.
        startListingDetailsActivity(currentListing);

    }

    private Listing getCurrentListing(int itemPosition){

        if(listings != null){

            return listings.get(itemPosition);

        }else{

            System.out.println("Error: listings was not set in RecyclerViewListener.");
            return null;
        }
    }

    private void startListingDetailsActivity(Listing currentListing){

        //We need to get the data from the current listing's object.

        int id = currentListing.getId();
        String title = currentListing.getTitle();
        String description = currentListing.getDescription();
        String createDate = currentListing.getCreateDate();
        String lastEditDate = currentListing.getLastEditDate();
        String userId = currentListing.getUserId();
        String priceCategoryId = currentListing.getPriceCategoryId();
        String[] images = currentListing.getImages();
        double price = currentListing.getPrice();
        boolean active = currentListing.getActive();
        String serializedObj = (new Gson()).toJson(currentListing);

        //Create the new Intent object.
        Intent intent = new Intent();
        intent.setClass(contextForListener, ListingDetailsActivity.class);
        intent.putExtra("listing", serializedObj);

        contextForListener.startActivity(intent);
    }

    public void setListingsAdapter(ListingsAdapter theListingsAdapter){

        listingsAdapter = theListingsAdapter;
    }

    public void setRecyclerView(RecyclerView view){

        searchRecyclerView = view;
    }

    public void setListings(ArrayList<Listing> newListings){

        listings = newListings;
    }

    public void setContext(Context theContext){

        contextForListener = theContext;
    }
}
