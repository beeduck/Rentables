package com.rentables.testcenter.fragment;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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

public class HomeFragment extends Fragment implements ThreadListener {

    private RecyclerView homeRecyclerView;
    private RecyclerView.LayoutManager homeLayoutManager;
    private ListingsAdapter listingsAdapter;
    private Listings currentUserListings = new Listings();
    private ArrayList<Listing> theListings;
    private RentRequest rentRequest;
    private boolean threadFlag;
    private View view;
    private Paint p = new Paint();

    private Thread thread = null;
    private Thread requestThread = null;
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
        initSwipe();

    }

    public void getCurrentUsersListings(){

        if(thread == null){

            fragmentProgressDialog.show();

            currentUserListings = new Listings();
            currentUserListings.setUserId(MainActivity.CURRENT_USER.getUserId());
            rentRequest = new RentRequest();
            rentRequest.setOption(RentRequestRouteOptions.REQUEST_FOR_USER);
            ServerConnection<Listings> connection = new ServerConnection<>(currentUserListings);
            connection.addListener(this);
            ServerConnection<RentRequest> rentRequestServerConnection = new ServerConnection<>(rentRequest);
            rentRequestServerConnection.addListener(this);
            thread = new Thread(connection);
            thread.start();
            requestThread = new Thread(rentRequestServerConnection);
            requestThread.start();

        }
    }

    @Override
    public void notifyOfThreadCompletion(NotifyingThread notifyThread) {

        if(!threadFlag) {
            threadFlag = true;
        }
        else {
            final ArrayList<Listing> listingsToView = new ArrayList<>();
            for(RentRequest r : rentRequest.getRentRequests()) {
                for(Listing e : currentUserListings.getListings()) {
                    if(r.getListingId() == e.getId()) {
                        listingsToView.add(e);
                    }
                }
            }
            if (this.getActivity() != null) {
                this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        listingsAdapter.updateDataSet(listingsToView);
                        listingsAdapter.notifyDataSetChanged();
                        fragmentProgressDialog.dismiss();

                    }
                });
            }
        }
    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if(direction == ItemTouchHelper.LEFT) {
                    removeView();
                }
                else {
                    removeView();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                    if(dX < 0) {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                    }
                    else if(dX > 0) {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(homeRecyclerView);
    }

    private void removeView(){
        if(view.getParent()!=null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }
}