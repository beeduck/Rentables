package com.rentables.testcenter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        parent = (ViewGroup) container.getParent();
        Toolbar toolbar = (Toolbar) parent.findViewById(R.id.toolbar_main);
        toolbar.getMenu().findItem(R.id.search_for_browse_fragment).setVisible(true).expandActionView();
        View rootView = (View) inflater.inflate(R.layout.fragment_browse, container, false);

        browseRecyclerView = (RecyclerView) rootView.findViewById(R.id.search_recycler_view);

        browseLayoutManager = new LinearLayoutManager(this.getContext());
        browseRecyclerView.setLayoutManager(browseLayoutManager);

        theListings = listings.getListings();
        browseListingsAdapter = new ListingsAdapter(theListings, inflater);
        browseRecyclerView.setAdapter(browseListingsAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        super.onViewCreated(view, savedInstanceState);

        onKeyListenerForSearch();
    }

    @Override
    public void onStop(){

        super.onStop();

        Toolbar toolbar = (Toolbar) parent.findViewById(R.id.toolbar_main);
        toolbar.getMenu().findItem(R.id.search_for_browse_fragment).collapseActionView();
        toolbar.getMenu().findItem(R.id.search_for_browse_fragment).setVisible(false);
    }

    public void runQueryOnDatabase(String query){

        if(queryThread == null) {

            listings.setKeywords(query);

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

        queryThread = null;
    }

    public void onKeyListenerForSearch(){

        final SearchView search = (SearchView) parent.findViewById(R.id.search_for_browse_fragment);
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
}
