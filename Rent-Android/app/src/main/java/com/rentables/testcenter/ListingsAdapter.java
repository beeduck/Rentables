package com.rentables.testcenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dataobject.Listing;

public class ListingsAdapter extends RecyclerView.Adapter<ListingsViewHolder> {

    private ArrayList<Listing> listings;
    private LayoutInflater adapterInflater;
    private RecyclerView searchRecyclerView;
    private Context currentContext;
    private final RecyclerViewListener listener = new RecyclerViewListener();

    public ListingsAdapter(ArrayList<Listing> l, LayoutInflater i, RecyclerView r, Context c){

        adapterInflater = i;
        listings = l;
        searchRecyclerView = r;
        currentContext = c;

        listener.setRecyclerView(searchRecyclerView);
        listener.setContext(c);
    }

    @Override
    public ListingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View listingsView = adapterInflater.inflate(R.layout.recyclerview_listing, parent, false);
        listingsView.setOnClickListener(listener);

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
        listingPrice.setText(createTextForPrice(position));

    }

    @Override
    public int getItemCount() {

        return listings.size();
    }

    public void setCurrentContext(Context theContext){

        currentContext = theContext;
    }

    public void updateDataSet(ArrayList<Listing> newListings){

        listings = newListings;
        listener.setListings(listings);
    }

    private String createTextForPrice(int position){

        Listing currentListing = listings.get(position);

        String price = String.valueOf(currentListing.getPrice());
        String priceCategoryId = convertCategoryId(currentListing.getPriceCategoryId());

        return "$" + price + " per " + priceCategoryId;
    }

    private String convertCategoryId(String categoryId){

        switch(categoryId){
            case "0":
                return "Hour";
            case "1":
                return "Day";
            case "2":
                return "Week";
            case "3":
                return "Month";
            default:
                return "Hour";
        }
    }
}
