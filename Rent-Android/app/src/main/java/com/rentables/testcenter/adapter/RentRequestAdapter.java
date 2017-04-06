package com.rentables.testcenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rentables.testcenter.R;
import com.rentables.testcenter.RentRequestViewHolder;

import java.util.List;

import dataobject.RentRequest;

/**
 * Created by Asad on 3/30/2017.
 */

public class RentRequestAdapter extends RecyclerView.Adapter<RentRequestViewHolder> {

    private List<RentRequest> rentRequests;

    public RentRequestAdapter(List<RentRequest> rentRequests) {
        this.rentRequests = rentRequests;
    }

    @Override
    public RentRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_rent_request, parent, false);
        return new RentRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RentRequestViewHolder holder, int position) {
        RentRequest request = rentRequests.get(position);
        View currentView = holder.getCurrentView();
        TextView requestListingId = (TextView)currentView.findViewById(R.id.rent_request_id);
        requestListingId.setText(String.valueOf(request.getRequestingUser()));
    }

    @Override
    public int getItemCount() {
        return rentRequests.size();
    }
}
