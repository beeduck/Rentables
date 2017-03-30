package com.rentables.testcenter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.rentables.testcenter.R;
import com.rentables.testcenter.RecyclerViewListener;

public class RentRequestsActivity extends AppCompatActivity {

    private RecyclerView rentRequestRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_requests);
        rentRequestRecyclerView =
    }
}
