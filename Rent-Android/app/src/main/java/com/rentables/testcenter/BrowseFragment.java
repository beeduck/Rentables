package com.rentables.testcenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dataobject.Listing;
import dataobject.Listings;
import server.NotifyingThread;
import server.ServerConnection;
import server.ThreadListener;

public class BrowseFragment extends Fragment implements ThreadListener {

    private Listings listings = null;
    private ListingsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_browse, container, false);
        ListView listView = (ListView) rootView.findViewById(android.R.id.list);

        listings = new Listings();
        listings.setListings(new ArrayList<Listing>());
        listings.setUserId(4);

        adapter = new ListingsAdapter(this.getContext(), R.layout.listview_listing, listings.getListings());
        listView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        super.onViewCreated(view, savedInstanceState);
        onKeyListenerForSearch();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();

    }

    public void onKeyListenerForSearch(){

        final SearchView search = (SearchView) this.getActivity().findViewById(R.id.browse_search_view);

        System.out.println("Reached");

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

    public void runQueryOnDatabase(String query){

        listings = new Listings();
        listings.setKeywords(query);

        ServerConnection<Listings> connection = new ServerConnection<>(listings);
        connection.addListener(this);

        Thread queryThread = new Thread(connection);
        queryThread.start();
    }

    @Override
    public void notifyOfThreadCompletion(NotifyingThread notifyThread) {

        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                adapter.addAll(listings.getListings());
            }
        });
    }
}