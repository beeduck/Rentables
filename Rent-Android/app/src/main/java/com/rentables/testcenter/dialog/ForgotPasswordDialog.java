package com.rentables.testcenter.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rentables.testcenter.R;

public class ForgotPasswordDialog extends DialogFragment {

    public ForgotPasswordDialog(){
        //Empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogThemeNoTitle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.dialog_fragment_forgot_pass, container);

        getDialog().setTitle("Enter Username!");

        return view;
    }

}
