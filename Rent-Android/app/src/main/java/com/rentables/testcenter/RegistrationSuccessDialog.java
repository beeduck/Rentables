package com.rentables.testcenter;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RegistrationSuccessDialog extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setStyle(DialogFragment.STYLE_NORMAL,  R.style.DialogThemeWithTitle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.dialog_fragment_successful_registration, container);

        getDialog().setTitle("Registration Successful!");

        return view;
    }
}
