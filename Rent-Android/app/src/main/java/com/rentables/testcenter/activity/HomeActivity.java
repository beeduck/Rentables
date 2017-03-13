package com.rentables.testcenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;

import com.rentables.testcenter.PayPalPaymentActivity;
import com.rentables.testcenter.dialog.AdvancedSearchDialog;
import com.rentables.testcenter.R;
import com.rentables.testcenter.fragment.BrowseFragment;
import com.rentables.testcenter.fragment.HomeFragment;
import com.rentables.testcenter.fragment.MyPostsFragment;
import com.rentables.testcenter.fragment.RentRequestFragment;


public class HomeActivity extends AppCompatActivity {

    //Only want to be able to run one thread at a time for creating posts.
    private Fragment currentFragment;
    private Fragment browseFragment = null;
    private AdvancedSearchDialog advancedSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
        toolbarMain.setTitle(getString(R.string.app_name));
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
            case R.id.overflow_advanced_search_option:
                clearSearchText();
                displayAdvancedSearchDialog();
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

    private void clearSearchText(){

        View view = findViewById(R.id.search_for_browse_fragment);

        if(view.getClass() == SearchView.class){

            SearchView searchView = (SearchView) view;
            searchView.setQuery("", false);
        }
    }

    private void displayAdvancedSearchDialog(){

        advancedSearch = new AdvancedSearchDialog();
        FragmentManager fm = getSupportFragmentManager();

        advancedSearch.show(fm, "advanced_search");
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

    public void toPaymentActivity(View view) {
        Intent paymentIntent = new Intent();
        paymentIntent.setClass(this, PayPalPaymentActivity.class);
        startActivity(paymentIntent);
    }

    public void onRequestButtonClick(View view) {
        currentFragment = new RentRequestFragment();
        addToBackStack();
    }

    public void selectFrag(View view) {

        if (view == findViewById(R.id.button1)) {
            currentFragment = new HomeFragment();
        } else if (view == findViewById(R.id.button3)) {
            if(browseFragment == null){browseFragment = new BrowseFragment();}
            currentFragment = browseFragment;
        } else if (view == findViewById(R.id.button4)) {
            currentFragment = new MyPostsFragment();
        } else {
            currentFragment = new HomeFragment();
        }

        addToBackStack();
    }

    private void addToBackStack(){


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if(checkBackStack(fm)){

            fragmentTransaction.replace(R.id.home_fragment_holder, currentFragment);
            fragmentTransaction.addToBackStack(currentFragment.getClass().toString());
            fragmentTransaction.commit();

        }
    }

    private boolean checkBackStack(FragmentManager fm){

        int position = fm.getBackStackEntryCount() - 1;

        if(position >= 0){

            String name = fm.getBackStackEntryAt(position).getName();
            String name2 = currentFragment.getClass().toString();

            if(name.equalsIgnoreCase(currentFragment.getClass().toString())){

                return false;
            }else{

                return true;
            }
        }

        return true;
    }

    public void userLogout(){

        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void callbackForBrowseFragment(View v){

        //Callback method for Advanced Search dialog.

        View parent = (View) v.getParent();
        EditText queryEditText = (EditText) parent.findViewById(R.id.edit_text_advanced_search);
        EditText minEditText = (EditText) parent.findViewById(R.id.edit_text_advanced_search_minimum);
        EditText maxEditText = (EditText) parent.findViewById(R.id.edit_text_advanced_search_maximum);

        String query = queryEditText.getText().toString();
        String minimum = minEditText.getText().toString();
        String maximum = maxEditText.getText().toString();
        String rentFor = String.valueOf(findCheckedRadioButton(parent));

        if(checkInputs(minimum, maximum)){

            advancedSearch.dismiss();
            BrowseFragment browseFragment = (BrowseFragment) currentFragment;

            browseFragment.runAdvancedQueryOnDatabase(query, minimum, maximum, rentFor);
        }else{

            maxEditText.setError("Minimum must be less than maximum.");
            maxEditText.setText("");
            maxEditText.requestFocus();
            return;
        }

        advancedSearch.dismiss();
        BrowseFragment browseFragment = (BrowseFragment) currentFragment;

        browseFragment.runAdvancedQueryOnDatabase(query, minimum, maximum, rentFor);
    }

    private int findCheckedRadioButton(View v){

        //Method for finding which RadioButton was checked in the Advanced Search dialog.

        RadioButton rHours = (RadioButton) v.findViewById(R.id.radio_button_hours);
        RadioButton rDays = (RadioButton) v.findViewById(R.id.radio_button_days);
        RadioButton rWeeks = (RadioButton) v.findViewById(R.id.radio_button_weeks);
        RadioButton rMonths = (RadioButton) v.findViewById(R.id.radio_button_months);

        if(rHours.isChecked()){

            return 1;
        }else if(rDays.isChecked()){

            return 2;
        }else if(rWeeks.isChecked()){

            return 3;
        }else if(rMonths.isChecked()){

            return 4;
        }

        return 0;
    }

    private boolean checkInputs(String minimum, String maximum){

        if(minimum.length() == 0 || maximum.length() == 0){

            return true;
        }

        double min = Double.parseDouble(minimum);
        double max = Double.parseDouble(maximum);

        if(min < max){
            return true;
        }else{
            return false;
        }
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
