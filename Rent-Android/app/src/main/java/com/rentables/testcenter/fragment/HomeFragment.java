package com.rentables.testcenter.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rentables.testcenter.ListingsAdapter;
import com.rentables.testcenter.R;
import com.rentables.testcenter.activity.MainActivity;

import java.util.ArrayList;

import dataobject.Listing;
import dataobject.Listings;
import server.NotifyingThread;
import server.ServerConnection;
import server.ThreadListener;

public class HomeFragment extends Fragment implements ThreadListener {

    private RecyclerView homeRecyclerView;
    private RecyclerView.LayoutManager homeLayoutManager;
    private ListingsAdapter listingsAdapter;
    private Listings currentUserListings = new Listings();
    private ArrayList<Listing> theListings;

    private Thread thread = null;
    private ProgressDialog fragmentProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeRecyclerView = (RecyclerView) view.findViewById(R.id.home_fragment_recycler_view);

        homeLayoutManager = new LinearLayoutManager(this.getContext());
        homeRecyclerView.setLayoutManager(homeLayoutManager);

        theListings = currentUserListings.getListings();
        listingsAdapter = new ListingsAdapter(theListings, inflater, homeRecyclerView, this.getContext(), R.layout.recycler_view_listing_home);
        homeRecyclerView.setAdapter(listingsAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        super.onViewCreated(view, savedInstanceState);

        fragmentProgressDialog = new ProgressDialog(this.getContext(), R.style.ProgressDialogTheme);
        fragmentProgressDialog.setCancelable(false);

        getCurrentUsersListings();

    }

    public void getCurrentUsersListings(){

        if(thread == null){

            fragmentProgressDialog.show();

            currentUserListings = new Listings();
            currentUserListings.setUserId(MainActivity.CURRENT_USER.getUserId());

            ServerConnection<Listings> connection = new ServerConnection<>(currentUserListings);
            connection.addListener(this);

            thread = new Thread(connection);
            thread.start();
        }
    }

    @Override
    public void notifyOfThreadCompletion(NotifyingThread notifyThread) {

        if(this.getActivity() != null){
            this.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    listingsAdapter.updateDataSet(currentUserListings.getListings());
                    listingsAdapter.notifyDataSetChanged();
                    fragmentProgressDialog.dismiss();

                }
            });
        }
    }
}