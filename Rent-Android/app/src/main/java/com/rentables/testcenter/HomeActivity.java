package com.rentables.testcenter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

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


    public void selectFrag(View view) {
        Fragment fr;

        if (view == findViewById(R.id.button1)) {
            fr = new HomeFragment();
        } else if (view == findViewById(R.id.button2)) {
            fr = new ButtonTwoFragment();
        } else if (view == findViewById(R.id.button3)) {
            fr = new BrowseFragment();
        } else if (view == findViewById(R.id.button4)) {
            fr = new MyPostsFragment();
        } else if (view == findViewById(R.id.button5)) {
            fr = new ButtonFiveFragment();
        } else
            fr = new HomeFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fr);
        fragmentTransaction.commit();

    }

    public void userLogout(){

        Intent intent = new Intent();
        intent.setClass(this, com.rentables.testcenter.MainActivity.class);
        startActivity(intent);
    }

}
