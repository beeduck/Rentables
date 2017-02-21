package com.rentables.testcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    //Only want to be able to run one thread at a time for creating posts.
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
        toolbarMain.setTitle("Rentables");
        toolbarMain.inflateMenu(R.menu.overflow_menu);
        toolbarMain.getMenu().findItem(R.id.search_for_browse_fragment).setVisible(false);
        setSupportActionBar(toolbarMain);

        Toolbar toolbarNavigate = (Toolbar) findViewById(R.id.toolbar_navigate);

        setSupportActionBar(toolbarNavigate);

        selectFrag(null);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){

        System.out.println(menu);

        return false;
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
            default:
                return true;

        }
    }

    @Override
    public void onBackPressed(){

        if(getSupportFragmentManager().getBackStackEntryCount() > 0){

            getSupportFragmentManager().popBackStack();

        }else{

            super.onBackPressed();
        }
    }

    public void toListingCreationActivity(View view){

        Intent listingCreationIntent = new Intent();
        listingCreationIntent.setClass(this, CreateListingActivity.class);

        startActivity(listingCreationIntent);
    }

    public void toSettingsActivity(){

        Intent settingsIntent = new Intent();
        settingsIntent.setClass(this, SettingsActivity.class);

        startActivity(settingsIntent);
    }

    public void selectFrag(View view) {
        Fragment fr;

        if (view == findViewById(R.id.button1)) {
            fr = new HomeFragment();
        } else if (view == findViewById(R.id.button3)) {
            fr = new BrowseFragment();
        } else if (view == findViewById(R.id.button4)) {
            fr = new MyPostsFragment();
        } else {
            fr = new HomeFragment();
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment_holder, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void userLogout(){

        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
