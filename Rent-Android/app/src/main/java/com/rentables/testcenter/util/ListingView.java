package com.rentables.testcenter.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import dataobject.Listing;
import dataobject.RentRequest;

/**
 * Created by Asad on 3/16/2017.
 */

public class ListingView extends RelativeLayout {

    private Listing listing;
    private RentRequest rentRequest;

    public ListingView(Context context) {
        super(context);
    }

    public ListingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ListingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ListingView(Context context, ViewGroup parent, int resourceId) {
        super(context);
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(resourceId, parent, true);
    }

    public RentRequest getRentRequest() {
        return rentRequest;
    }

    public void setRentRequest(RentRequest rentRequest) {
        this.rentRequest = rentRequest;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }
}
