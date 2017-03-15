package com.rentables.testcenter.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rentables.testcenter.ListingsAdapter;
import com.rentables.testcenter.R;
import com.rentables.testcenter.activity.MainActivity;
import com.rentables.testcenter.util.RentRequestRouteOptions;

import java.util.ArrayList;
import java.util.List;

import dataobject.Listing;
import dataobject.Listings;
import dataobject.RentRequest;
import server.NotifyingThread;
import server.ServerConnection;
import server.ThreadListener;

public class RentRequestFragment extends Fragment implements ThreadListener{

    private RecyclerView browseRecyclerView;
    private RecyclerView.LayoutManager browseLayoutManager;
    private ListingsAdapter browseListingsAdapter;
    private RentRequest rentRequest;
    private List<RentRequest> rentRequests;
    private Thread queryThread;
    private ViewGroup parent;

    public RentRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = (ViewGroup) container.getParent();
        Toolbar toolbar = (Toolbar) parent.findViewById(R.id.toolbar_main);
        // Inflate the layout for this fragment
        View rootView =  (View)inflater.inflate(R.layout.fragment_rent_request, container, false);
        browseRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_requests_recycler_view);

        browseLayoutManager = new LinearLayoutManager(this.getContext());
        browseRecyclerView.setLayoutManager(browseLayoutManager);
        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        rentRequest = new RentRequest();
        rentRequest.setOption(RentRequestRouteOptions.REQUEST_FOR_USER);
        ServerConnection<RentRequest> connection = new ServerConnection<>(rentRequest);
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
//
//                    browseListingsAdapter.updateDataSet(rentRequest.getRentRequests());
//                    browseListingsAdapter.notifyDataSetChanged();
                    Log.i("it worked","Success");
                }
            });
        }

        queryThread = null;
    }
}
