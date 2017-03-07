package com.rentables.testcenter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rentables.testcenter.ListingsAdapter;
import com.rentables.testcenter.activity.MainActivity;
import com.rentables.testcenter.R;

import java.util.ArrayList;

import dataobject.Listing;
import dataobject.Listings;
import server.NotifyingThread;
import server.ServerConnection;
import server.ThreadListener;

public class MyPostsFragment extends Fragment implements ThreadListener {

    private RecyclerView browseRecyclerView;
    private RecyclerView.LayoutManager browseLayoutManager;
    private ListingsAdapter browseListingsAdapter;
    private Listings listings = new Listings();
    private ArrayList<Listing> theListings;
    private Thread queryThread;
    private ViewGroup parent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        parent = (ViewGroup) container.getParent();
        Toolbar toolbar = (Toolbar) parent.findViewById(R.id.toolbar_main);
        //toolbar.getMenu().findItem(R.id.search_for_browse_fragment).setVisible(true).expandActionView();
        View rootView = (View) inflater.inflate(R.layout.fragment_myposts, container, false);

        browseRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_listings_recycler_view);

        browseLayoutManager = new LinearLayoutManager(this.getContext());
        browseRecyclerView.setLayoutManager(browseLayoutManager);

        theListings = listings.getListings();
        browseListingsAdapter = new ListingsAdapter(theListings, inflater, browseRecyclerView, this.getContext());
        browseRecyclerView.setAdapter(browseListingsAdapter);

        return rootView;
        // return inflater.inflate(R.layout.fragment_myposts, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState){

        super.onViewCreated(view, savedInstanceState);
        listings.setKeywords("");
        listings.setUserId(MainActivity.CURRENT_USER.getUserId());

        ServerConnection<Listings> connection = new ServerConnection<>(listings);
        connection.addListener(this);

        queryThread = new Thread(connection);
        queryThread.start();

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
}