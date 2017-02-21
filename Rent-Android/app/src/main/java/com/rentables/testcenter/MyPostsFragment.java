package com.rentables.testcenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import dataobject.Listing;
import dataobject.Listings;

public class MyPostsFragment extends Fragment {

    private Listings listings = new Listings();
    private ListingsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        listings.setListings(new ArrayList<Listing>());
        listings.setUserId(4);

        View rootView = inflater.inflate(R.layout.fragment_myposts, container, false);
        ListView listView = (ListView) rootView.findViewById(android.R.id.list);

        //listings = new Listings();
       // listings.setListings(new ArrayList<Listing>());
        //listings.setUserId(4);

        adapter = new ListingsAdapter(this.getContext(), R.layout.listview_listing, listings.getListings());
        listView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return rootView;
    }
}