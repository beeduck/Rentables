package com.rentables.testcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.concurrent.locks.ReentrantLock;

import dataobject.CreateListing;
import server.NotifyingThread;
import server.ServerConnection;
import server.ThreadListener;

public class HomeActivity extends AppCompatActivity implements ThreadListener {

    //Only want to be able to run one thread at a time for creating posts.
    private Thread runningThread = null;

    @Override
    protected void onCreate(Bundle savedInstance){

        super.onCreate(savedInstance);
        setContentView(R.layout.activity_home);

        Toolbar toolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
        toolbarMain.inflateMenu(R.menu.overflow_menu);
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setTitle("Rentables");



        Toolbar toolbarNavigate = (Toolbar) findViewById(R.id.toolbar_navigate);

        setSupportActionBar(toolbarNavigate);

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
                showPostCreatedDialog();
            }
        });

        System.out.println("Should only reach this once");
    }

    public void selectFrag(View view) {
        Fragment fr;

        if (view == findViewById(R.id.button1)) {
            fr = new HomeFragment();
        } /*else if (view == findViewById(R.id.button2)) {
            fr = new ButtonTwoFragment();
        }*/ else if (view == findViewById(R.id.button3)) {
            fr = new BrowseFragment();
        } else if (view == findViewById(R.id.button4)) {
            fr = new MyPostsFragment();
        }/* else if (view == findViewById(R.id.button5)) {
            fr = new ButtonFiveFragment();
        }*/ else if (view == findViewById(R.id.create_post_button)) {
            fr = new CreatePostFragment();
        } else
            fr = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fr);
        fragmentTransaction.commit();

    }

    public void userLogout(){

        Intent intent = new Intent();
        intent.setClass(this, com.rentables.testcenter.MainActivity.class);
        startActivity(intent);
    }

    public void createListing(View view){

        //Method for creating a post.
        //TODO UserId needs to be fixed.

        System.out.println("Reached");

        if(runningThread == null) {

            CreateListing theListing = new CreateListing();

            EditText titleText = (EditText) findViewById(R.id.title_text);
            EditText descriptionText = (EditText) findViewById(R.id.description_text);
            EditText priceText = (EditText) findViewById(R.id.create_post_price);
            Spinner priceCategoryIdText = (Spinner) findViewById(R.id.rental_time_spinner);

            String title = titleText.getText().toString().trim();
            String description = descriptionText.getText().toString().trim();
            String price = priceText.getText().toString().trim();
            String priceCategoryId = priceCategoryIdText.getSelectedItem().toString();
            int userId = 3; //A placeholder for now.

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

    private void showPostCreatedDialog(){

        FragmentManager manager = getSupportFragmentManager();
        CreatePostDialog successDialog = new CreatePostDialog();
        successDialog.show(manager, "dialog_create_post_successful");
    }

    public void onPostCreation(View view){

        runningThread = null;
        FragmentManager fm = getSupportFragmentManager();
        Fragment fr = new HomeFragment();
        DialogFragment up = (DialogFragment) fm.findFragmentByTag("dialog_create_post_successful");
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if(up != null){

            fragmentTransaction.remove(up);
        }

        fragmentTransaction.replace(R.id.fragment_place, fr);
        fragmentTransaction.commit();

    }
}
