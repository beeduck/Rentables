package com.rentables.testcenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import dataobject.CreateListing;
import server.NotifyingThread;
import server.ServerConnection;
import server.ThreadListener;

public class CreateListingActivity extends AppCompatActivity implements ThreadListener{

    private Thread runningThread = null;
    private ProgressDialog listingCreationProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        Toolbar toolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setTitle("Rentables");

        Spinner spinner = (Spinner) findViewById(R.id.rental_time_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this ,
                R.array.RentalTime, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Spinner spinner2 = (Spinner) findViewById(R.id.time_increment_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this ,
                R.array.RentalTime, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.overflow_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.overflow_settings_option:
                System.out.println("NICE");
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

        System.out.println("Should only reach this once");
    }

    public void createListing(View view){

        //Method for creating a post.
        //TODO UserId needs to be fixed.

        if(runningThread == null) {

            showListingCreationProgressDialog();

            CreateListing theListing = new CreateListing();

            EditText titleText = (EditText) findViewById(R.id.title_text);
            EditText descriptionText = (EditText) findViewById(R.id.description_text);
            EditText priceText = (EditText) findViewById(R.id.create_post_price);
            Spinner priceCategoryIdText = (Spinner) findViewById(R.id.rental_time_spinner);

            String title = titleText.getText().toString().trim();
            String description = descriptionText.getText().toString().trim();
            String price = priceText.getText().toString().trim();
            String priceCategoryId = priceCategoryIdText.getSelectedItem().toString();
            int userId = 4; //A placeholder for now.

            theListing.setTitle(title);
            theListing.setDescription(description);
            theListing.setPrice(Double.parseDouble(price));
            theListing.setPriceCategoryId(getCorrectPriceCategoryId(priceCategoryId));
            theListing.setUserId(userId);

            ServerConnection<CreateListing> connection = new ServerConnection<>(theListing);
            connection.addListener(this);

            runningThread = new Thread(connection);
            runningThread.start();
            System.out.println("Reached Behind the wall!");
        }
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
        System.out.println("Hello");
    }

    public void onPostCreation(View view){

        Intent homeIntent = new Intent();
        homeIntent.setClass(this, HomeActivity.class);

        startActivity(homeIntent);
    }

    public void userLogout(){

        Intent intent = new Intent();
        intent.setClass(this, com.rentables.testcenter.MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
