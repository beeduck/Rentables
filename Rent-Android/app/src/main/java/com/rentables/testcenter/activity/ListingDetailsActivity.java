package com.rentables.testcenter.activity;

//The Listings Details landing activity.

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rentables.testcenter.PayPalPaymentActivity;
import com.rentables.testcenter.R;

import dataobject.Listing;
import server.ServerConnection;

public class ListingDetailsActivity extends AppCompatActivity {

    private final Listing currentListing = new Listing();

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_details);

        //Recreate the listing passed to the activity.
        createListingFromIntent();
        setImage();
        setText();

        //Setup the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle(currentListing.getTitle());
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.overflow_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.overflow_settings_option:
                toSettingsActivity();
                return true;
            case R.id.overflow_account_option:
                System.out.println("NICE");
                return true;
            case R.id.overflow_logout_option:
                userLogout();
                return true;
            case R.id.overflow_advanced_search_option:
                return true;
            default:
                return true;

        }
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

    private void setImage(){

        ImageView imageView = (ImageView) findViewById(R.id.listing_details_image_for_listing);
        String[] images = currentListing.getImages();

        if(images.length != 0){

            Glide.with(this).load(ServerConnection.LISTING_IMAGES + "/" + images[0]).into(imageView);
        }
    }

    private void setText() {

        TextView title = (TextView) findViewById(R.id.listing_details_title);
        TextView description = (TextView) findViewById(R.id.listing_details_description);
        TextView price = (TextView) findViewById(R.id.listing_details_price);

        title.setText(currentListing.getTitle().trim());
        description.setText(currentListing.getDescription().trim());
        price.setText("$" + currentListing.getPrice() + " per " + convertCategoryId(currentListing.getPriceCategoryId()));
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

    public void toSettingsActivity(){

        Intent settingsIntent = new Intent();
        settingsIntent.setClass(this, SettingsActivity.class);

        startActivity(settingsIntent);
    }

    public void toPayPalPaymentActivity(View view) {
        Intent paymentIntent = new Intent();
        paymentIntent.setClass(this, PayPalPaymentActivity.class);
        Bundle listingBundle = new Bundle();
        listingBundle.putString("title",currentListing.getTitle());
        listingBundle.putString("price", String.valueOf(currentListing.getPrice()));
        listingBundle.putString("priceCategory",convertCategoryId(currentListing.getPriceCategoryId()));
        listingBundle.putInt("id",currentListing.getId());
        paymentIntent.putExtras(listingBundle);
        startActivity(paymentIntent);
    }

    public void userLogout(){

        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
