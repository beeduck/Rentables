package com.rentables.testcenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

import dataobject.Listing;
import dataobject.Listings;
import server.NotifyingThread;
import server.ServerConnection;
import server.ThreadListener;

public class BrowseFragment extends Fragment implements ThreadListener{

    private RecyclerView browseRecyclerView;
    private RecyclerView.LayoutManager browseLayoutManager;
    private ListingsAdapter browseListingsAdapter;
    private Listings listings = new Listings();
    private ArrayList<Listing> theListings;
    private Thread queryThread;
    private ViewGroup parent;
    private ProgressDialog fragmentProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        parent = (ViewGroup) container.getParent();

        Toolbar toolbar = (Toolbar) parent.findViewById(R.id.toolbar_main);
        Menu menu = toolbar.getMenu();
        menu.findItem(R.id.search_for_browse_fragment).setVisible(true).expandActionView();
        menu.findItem(R.id.overflow_advanced_search_option).setVisible(true);

        View rootView = (View) inflater.inflate(R.layout.fragment_browse, container, false);

        browseRecyclerView = (RecyclerView) rootView.findViewById(R.id.search_recycler_view);

        browseLayoutManager = new LinearLayoutManager(this.getContext());
        browseRecyclerView.setLayoutManager(browseLayoutManager);

        theListings = listings.getListings();
        browseListingsAdapter = new ListingsAdapter(theListings, inflater, browseRecyclerView, this.getContext());
        browseListingsAdapter.setCurrentContext(this.getContext());
        browseRecyclerView.setAdapter(browseListingsAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        super.onViewCreated(view, savedInstanceState);

        fragmentProgressDialog = new ProgressDialog(this.getContext(), R.style.ProgressDialogTheme);
        fragmentProgressDialog.setCancelable(false);

        onKeyListenerForSearch();
    }

    @Override
    public void onPause(){

        super.onPause();

        Toolbar toolbar = (Toolbar) parent.findViewById(R.id.toolbar_main);
        toolbar.getMenu().findItem(R.id.search_for_browse_fragment).collapseActionView();
        toolbar.getMenu().findItem(R.id.search_for_browse_fragment).setVisible(false);
        toolbar.getMenu().findItem(R.id.overflow_advanced_search_option).setVisible(false);
    }

    public void runQueryOnDatabase(String query){

        if(queryThread == null) {

            fragmentProgressDialog.show();

            listings = new Listings();
            listings.setKeywords(query);

            ServerConnection<Listings> connection = new ServerConnection<>(listings);
            connection.addListener(this);

            queryThread = new Thread(connection);
            queryThread.start();

        }
    }

    public void runAdvancedQueryOnDatabase(String query, String minimum, String maximum, String priceCategoryId){

        if(queryThread == null){

            fragmentProgressDialog.show();

            listings = new Listings();
            listings.setKeywords(query);
            listings.setMinPrice(minimum);
            listings.setMaxPrice(maximum);
            listings.setPriceCategoryId(priceCategoryId);

            ServerConnection<Listings> connection = new ServerConnection<>(listings);
            connection.addListener(this);

            queryThread = new Thread(connection);
            queryThread.start();
        }
    }

    @Override
    public void notifyOfThreadCompletion(NotifyingThread notifyThread) {

        if(this.getActivity() != null) {

            this.getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    browseListingsAdapter.updateDataSet(listings.getListings());
                    browseListingsAdapter.notifyDataSetChanged();
                }
            });
        }

        fragmentProgressDialog.dismiss();
        clearFocusOnSearch();
        queryThread = null;
    }

    public void onKeyListenerForSearch(){

        final SearchView search = (SearchView) parent.findViewById(R.id.search_for_browse_fragment);
        search.clearFocus();
        search.setQueryHint("Search");

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                runQueryOnDatabase(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Possible implementation of search when user is changing the query
                return false;
            }
        });
    }

    private void clearFocusOnSearch(){

        //This clears the focus on the SearchWidget if it exists.

        final SearchView searchView = (SearchView) getActivity().findViewById(R.id.search_for_browse_fragment);

        if(searchView != null){

            this.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    searchView.clearFocus();
                }
            });
        }
    }

    private void hideKeyboard(){

        final InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        if(imm != null){

            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
    }
}
