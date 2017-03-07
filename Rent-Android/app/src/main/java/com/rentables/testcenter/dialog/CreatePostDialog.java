package com.rentables.testcenter.dialog;


import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rentables.testcenter.R;

public class CreatePostDialog extends DialogFragment{

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setStyle(DialogFragment.STYLE_NORMAL,  R.style.DialogThemeWithTitle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.dialog_fragment_create_post, container);

        getDialog().setTitle("Post Created!");

        return view;
    }
}
