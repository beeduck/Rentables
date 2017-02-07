package com.rentables.testcenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import dataobject.Listing;

public class ListingsAdapter extends ArrayAdapter<Listing> {

    public ListingsAdapter(Context context, int resource, List<Listing> objects) {

        //Resource should be the view that will be inflated for each piece of data
        super(context, resource, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View currentView = convertView;

        if(currentView == null){

            //Checking to see if the view can be reused. If there is no view, then it needs to be inflated.
            LayoutInflater inflater = LayoutInflater.from(getContext());
            currentView = inflater.inflate(R.layout.listview_listing, null);
        }

        //The current item that contains the data for this particular view.
        Listing currentListing = getItem(position);

        //Dynamically adding data to the view.
        if(currentListing != null){

            TextView title = (TextView) currentView.findViewById(R.id.user_listing_title);
            TextView description = (TextView) currentView.findViewById(R.id.user_listing_description);
            TextView price = (TextView) currentView.findViewById(R.id.user_listing_price);

            title.setText(currentListing.getTitle());
            description.setText(currentListing.getDescription());
            price.setText(String.valueOf(currentListing.getPrice()));
        }

        //Return the view so that it can be displayed.
        return currentView;
    }
}
