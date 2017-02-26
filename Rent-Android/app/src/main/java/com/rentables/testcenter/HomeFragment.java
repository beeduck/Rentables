package com.rentables.testcenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View thisView = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView imageView = (ImageView) thisView.findViewById(R.id.imageView);
        if(imageView != null)
            Glide.with(this)
                    .load("http://rentapi.us-west-2.elasticbeanstalk.com/listing-images/48db029a-c2ac-44dd-9849-7d3cf562544e")
                    .into(imageView);

        return thisView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        if(imageView != null)
            Glide.with(this)
                    .load("http://rentapi.us-west-2.elasticbeanstalk.com/listing-images/48db029a-c2ac-44dd-9849-7d3cf562544e")
                    .into(imageView);

        super.onViewCreated(view, savedInstanceState);
    }
}