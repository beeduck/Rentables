package com.rentables.testcenter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import dataobject.Listing;
import dataobject.RentRequest;
import server.ServerConnection;

public class ListingsAdapter extends RecyclerView.Adapter<ListingsViewHolder> {

    private ArrayList<Listing> listings;
    private List<RentRequest> rentRequests;
    private LayoutInflater adapterInflater;
    private RecyclerView searchRecyclerView;
    private Context currentContext;
    private final RecyclerViewListener listener = new RecyclerViewListener();
    private int resourceID;
    private Listing toBeDeleted;

    public ListingsAdapter(ArrayList<Listing> l, LayoutInflater i, RecyclerView r, Context c, int id){

        adapterInflater = i;
        searchRecyclerView = r;
        currentContext = c;
        resourceID = id;

        this.updateDataSet(l);

        listener.setRecyclerView(searchRecyclerView);
        listener.setContext(c);
    }

    @Override
    public ListingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View listingsView = adapterInflater.inflate(resourceID, parent, false);
        listingsView.setOnClickListener(listener);

        return new ListingsViewHolder(listingsView);
    }

    @Override
    public void onBindViewHolder(ListingsViewHolder holder, int position) {

        if(resourceID == R.layout.recyclerview_listing) {

            setUpAdapterForBrowseFragment(holder, position);

        }else if(resourceID == R.layout.recycler_view_listing_home){

            setupAdapterForHomeFragment(holder, position);

        }else if(resourceID == R.layout.recycler_view_rent_request) {
            setupAdapterForRequestFragment(holder, position);
        }

        else{

            throw new RuntimeException("Error at: " + this.getClass().toString());
        }
    }

    private void setupAdapterForRequestFragment(ListingsViewHolder holder, int position) {
        RentRequest request = rentRequests.get(position);
        View currentView = holder.getCurrentView();
        TextView requestListingId = (TextView)currentView.findViewById(R.id.rent_request_id);
        requestListingId.setText(String.valueOf(request.getListingId()));
    }

    @Override
    public int getItemCount() {

        return listings.size();
    }

    public void onItemRemove(boolean confirm, final int position, final RecyclerView recyclerView) {
        Listing currentListing = listings.get(position);
        if(!confirm && toBeDeleted != null) {
            listings.add(position, toBeDeleted);
            notifyItemInserted(position);
            recyclerView.scrollToPosition(position);
            return;
        }
        toBeDeleted = currentListing;
        listings.remove(position);
        notifyItemRemoved(position);
    }

    private void setupAdapterForHomeFragment(ListingsViewHolder holder, int position){

        //Method for creating the Recycler View for the Home Fragment.

        Listing currentListing = listings.get(position);
        String[] images = currentListing.getImages();
        View currentView = holder.getCurrentView();
        String price = String.valueOf(currentListing.getPrice());
        String per = convertCategoryId(currentListing.getPriceCategoryId());

        TextView listingTitle = (TextView) currentView.findViewById(R.id.recycler_listing_home_title);
        TextView listingDescription = (TextView) currentView.findViewById(R.id.recycler_listing_home_description);
        TextView listingPrice = (TextView) currentView.findViewById(R.id.recycler_listing_home_price);
        ImageView listingImageView = (ImageView) currentView.findViewById(R.id.recycler_listing_home_thumbnail);

        listingTitle.setText(currentListing.getTitle());
        listingDescription.setText(currentListing.getDescription());
        listingPrice.setText("$" + price + " per " + per);

        if(images.length > 0){

            Glide
                    .with(currentContext)
                    .load(ServerConnection.LISTING_IMAGES + "/" + images[0])
                    .into(listingImageView);
        }else{

            Glide.clear(listingImageView);
        }
    }

    private void setUpAdapterForBrowseFragment(ListingsViewHolder holder, int position){

        //Method for creating the Recycler View for the Browse Fragment.

        Listing currentListing = listings.get(position);
        View currentView = holder.getCurrentView();

        TextView listingTitle = (TextView) currentView.findViewById(R.id.user_listing_title);
        TextView listingDescription = (TextView) currentView.findViewById(R.id.user_listing_description);
        TextView listingPrice = (TextView) currentView.findViewById(R.id.user_listing_price);

        listingTitle.setText(currentListing.getTitle());
        listingDescription.setText(currentListing.getDescription());
        listingPrice.setText(createTextForPrice(position));
    }

//    public void updateDataSetForRequests(List<RentRequest> requests) {
//        rentRequests = requests;
//        listener.setListings();
//    }

    private String createTextForPrice(int position){

        Listing currentListing = listings.get(position);

        String price = String.valueOf(currentListing.getPrice());
        String priceCategoryId = convertCategoryId(currentListing.getPriceCategoryId());

        return "$" + price + " per " + priceCategoryId;
    }

    private String convertCategoryId(String categoryId){

        switch(categoryId){
            case "1":
                return "Hour";
            case "2":
                return "Day";
            case "3":
                return "Week";
            case "4":
                return "Month";
            default:
                return "Hour";
        }
    }

    public void setCurrentContext(Context theContext){

        currentContext = theContext;
    }

    public void updateDataSet(ArrayList<Listing> newListings){

        listings = newListings;
        listener.setListings(listings);
    }
}
