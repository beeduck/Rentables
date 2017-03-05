package com.rentables.testcenter;

//The Listings Details landing activity.

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import dataobject.Listing;

public class ListingDetailsActivity extends AppCompatActivity {

    private Thread imageThread;
    private final Listing currentListing = new Listing();

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_details);

        //Recreate the listing passed to the activity.
        createListingFromIntent();
        setText();
    }

    private void createListingFromIntent(){

        //This method is meant to create the Listing off of the data that was passed to it
        //Through the intent.

        Bundle b = this.getIntent().getExtras();

        currentListing.setId(b.getInt("id"));
        currentListing.setTitle(b.getString("title"));
        currentListing.setDescription(b.getString("description"));
        currentListing.setCreateDate(b.getString("createDate"));
        currentListing.setLastEditDate(b.getString("lastEditDate"));
        currentListing.setUserId(b.getString("userId"));
        currentListing.setPriceCategoryId(b.getString("priceCategoryId"));
        currentListing.setImages(b.getStringArray("images"));
        currentListing.setPrice(b.getDouble("price"));
        currentListing.setActive(b.getBoolean("active"));
    }

    public void setText(){

        TextView title = (TextView) findViewById(R.id.listing_details_title);
        TextView description = (TextView) findViewById(R.id.listing_details_description);

        title.setText(currentListing.getTitle().trim());
        description.setText(currentListing.getDescription().trim());
    }
}
