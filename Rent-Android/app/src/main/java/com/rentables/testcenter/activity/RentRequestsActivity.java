package com.rentables.testcenter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.rentables.testcenter.R;
import com.rentables.testcenter.RecyclerViewListener;
import com.rentables.testcenter.adapter.RentRequestAdapter;

import dataobject.Listing;

public class RentRequestsActivity extends AppCompatActivity {

    private RecyclerView rentRequestRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_requests);
        rentRequestRecyclerView = (RecyclerView)findViewById(R.id.rent_request_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        rentRequestRecyclerView.setLayoutManager(layoutManager);
        Bundle bundle = this.getIntent().getExtras();
        Listing currentListing = new Gson().fromJson(bundle.getString("listing"), Listing.class);
        adapter = new RentRequestAdapter(currentListing.getRentRequests());
        rentRequestRecyclerView.setAdapter(adapter);
    }
}
