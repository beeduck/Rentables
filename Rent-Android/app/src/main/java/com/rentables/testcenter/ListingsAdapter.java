package com.rentables.testcenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dataobject.Listing;

public class ListingsAdapter extends RecyclerView.Adapter<ListingsViewHolder> {

    private ArrayList<Listing> listings;
    private LayoutInflater adapterInflater;

    public ListingsAdapter(ArrayList<Listing> l, LayoutInflater i){

        adapterInflater = i;
        listings = l;
    }

    @Override
    public ListingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View listingsView = adapterInflater.inflate(R.layout.recyclerview_listing, parent, false);

        return new ListingsViewHolder(listingsView);
    }

    @Override
    public void onBindViewHolder(ListingsViewHolder holder, int position) {

        Listing currentListing = listings.get(position);
        View currentView = holder.getCurrentView();

        TextView listingTitle = (TextView) currentView.findViewById(R.id.user_listing_title);
        TextView listingDescription = (TextView) currentView.findViewById(R.id.user_listing_description);
        TextView listingPrice = (TextView) currentView.findViewById(R.id.user_listing_price);

        listingTitle.setText(currentListing.getTitle());
        listingDescription.setText(currentListing.getDescription());
        listingPrice.setText(String.valueOf(currentListing.getPrice()));

    }

    @Override
    public int getItemCount() {

        return listings.size();
    }

    public void updateDataSet(ArrayList<Listing> newListings){

        listings = newListings;
    }
}
