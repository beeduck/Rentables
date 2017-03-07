package com.rentables.testcenter.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.rentables.testcenter.R;
import com.rentables.testcenter.dialog.CreatePostDialog;

import dataobject.CreateListing;
import server.NotifyingThread;
import server.ServerConnection;
import server.ThreadListener;

public class CreateListingActivity extends AppCompatActivity implements ThreadListener {

    private Thread currentThread = null;
    private ProgressDialog listingCreationProgress;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        setupToolbar();
        setTextForSpinners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.overflow_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

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
            default:
                return true;

        }
    }

    @Override
    public void notifyOfThreadCompletion(NotifyingThread notifyThread) {

        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                listingCreationProgress.dismiss();
                showPostCreatedDialog();
            }
        });
    }

    private void toSettingsActivity(){

        Intent settingsIntent = new Intent();
        settingsIntent.setClass(this, SettingsActivity.class);

        startActivity(settingsIntent);
    }

    private void userLogout(){

        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setupToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Rentables");
        setSupportActionBar(toolbar);

    }

    private void setTextForSpinners(){

        Spinner perSpinner = (Spinner) findViewById(R.id.create_listing_per_spinner);

        ArrayAdapter<CharSequence> perAdapter = ArrayAdapter.createFromResource(this, R.array.RentalTime, R.layout.spinner_item);

        perAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        perSpinner.setAdapter(perAdapter);

    }

    public void createListing(View view){

        if(currentThread == null) {

            //Retrieve all data from the form.
            EditText title = (EditText) findViewById(R.id.create_listing_title_edit_text);
            EditText description = (EditText) findViewById(R.id.create_listing_description_edit_text);
            EditText price = (EditText) findViewById(R.id.create_listing_price_edit_text);
            EditText additionalDetails = (EditText) findViewById(R.id.create_listing_additional_details_edit_text);
            Spinner per = (Spinner) findViewById(R.id.create_listing_per_spinner);

            String titleText = title.getText().toString().trim();
            String descriptionText = description.getText().toString().trim();
            String detailsText = additionalDetails.getText().toString().trim();
            int priceCategoryId = getCorrectPriceCategoryId(per.getSelectedItem().toString());
            String priceText = price.getText().toString().trim();

            if(checkAllInputs(titleText, descriptionText, detailsText, priceText, priceCategoryId)){

                //If the form checks out proceed with creating the Listing.
                CreateListing currentListing = new CreateListing();
                showListingCreationProgressDialog();

                currentListing.setTitle(titleText);
                currentListing.setDescription(descriptionText);
                currentListing.setPriceCategoryId(priceCategoryId);
                currentListing.setPrice(Double.parseDouble(priceText));

                //Create the ServerConnection object and start the thread.
                ServerConnection<CreateListing> connection = new ServerConnection<>(currentListing);
                connection.addListener(this);

                currentThread = new Thread(connection);
                currentThread.start();

            }else{

                hideKeyboard();
            }
        }
    }

    private boolean checkAllInputs(String titleText, String descriptionText, String details, String currentPrice, int priceCategoryId){

        EditText title = (EditText) findViewById(R.id.create_listing_title_edit_text);
        EditText description = (EditText) findViewById(R.id.create_listing_description_edit_text);
        EditText price = (EditText) findViewById(R.id.create_listing_price_edit_text);
        EditText additionalDetails = (EditText) findViewById(R.id.create_listing_additional_details_edit_text);
        Spinner per = (Spinner) findViewById(R.id.create_listing_per_spinner);

        boolean pass = true;

        if(titleText.length() == 0){

            title.setError("Please Enter a Title.");
            pass = false;
        }

        if(descriptionText.length() == 0){

            description.setError("Please Enter a Description.");
            pass = false;
        }

        if(currentPrice.length() == 0){

            price.setError("Please Enter a Price.");
            pass = false;
        }

        return pass;
    }

    private void showListingCreationProgressDialog(){

        listingCreationProgress = new ProgressDialog(CreateListingActivity.this, ProgressDialog.STYLE_SPINNER);
        listingCreationProgress.setTitle("Creating Listing...");
        listingCreationProgress.setMessage("please wait");
        listingCreationProgress.show();
    }

    private void showPostCreatedDialog(){

        FragmentManager manager = getSupportFragmentManager();
        CreatePostDialog successDialog = new CreatePostDialog();
        successDialog.show(manager, "dialog_create_post_successful");
    }

    public void onPostCreation(View view){

        Intent homeIntent = new Intent();
        homeIntent.setClass(this, HomeActivity.class);

        startActivity(homeIntent);
    }

    public int getCorrectPriceCategoryId(String priceCategoryId){

        switch (priceCategoryId.toLowerCase()){

            case "hours":
                return 1;
            case "days":
                return 2;
            case "weeks":
                return 3;
            case "months":
                return 4;
            case "semester":
                return 5;
        }

        return 1;
    }

    private void hideKeyboard(){

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if(imm != null){

            imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}
