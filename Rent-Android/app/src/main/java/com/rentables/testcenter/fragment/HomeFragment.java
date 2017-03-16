package com.rentables.testcenter.fragment;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
            for(Listing e : currentUserListings.getListings()) {
                if(!listingsToView.contains(e)) {
                    listingsToView.add(e);
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
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                final RentRequest request = rentRequest.getRentRequests().get(viewHolder.getAdapterPosition());
                listingsAdapter.onItemRemove(true, viewHolder.getAdapterPosition(), homeRecyclerView);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                if(direction == ItemTouchHelper.RIGHT) {
                    alertDialog.setTitle("Approve Rent Request");
                    alertDialog.setMessage("Are you sure you want to approve this request?");
                    alertDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            request.setOption(RentRequestRouteOptions.ACCEPT_REQUEST);
                            ServerConnection<RentRequest> connection = new ServerConnection<>(request);
                            requestThread = new Thread(connection);
                            requestThread.start();
                            Toast.makeText(getContext(), "Approved", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listingsAdapter.onItemRemove(false, position, homeRecyclerView);
                            Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialog.show();
                }
                else if(direction == ItemTouchHelper.LEFT){
                    alertDialog.setTitle("Deny Rent Request");
                    alertDialog.setMessage("Are you sure you want to deny this request?");
                    alertDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            request.setOption(RentRequestRouteOptions.DENY_REQUEST);
                            ServerConnection<RentRequest> connection = new ServerConnection<>(request);
                            requestThread = new Thread(connection);
                            requestThread.start();
                            Toast.makeText(getContext(), "Denied", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listingsAdapter.onItemRemove(false, position, homeRecyclerView);
                            Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                    Paint p = new Paint();
                    //Approve
                    if(dX > 0){
                        p.setColor(Color.parseColor("#4CAF50"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                    }
                    //Deny
                    else if(dX < 0) {
                        p.setColor(Color.parseColor("#F44336"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() > rentRequest.getRentRequests().size()-1) return 0;
                return makeMovementFlags(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(homeRecyclerView);
    }
}