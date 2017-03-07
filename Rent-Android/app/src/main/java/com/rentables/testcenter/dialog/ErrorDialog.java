package com.rentables.testcenter.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rentables.testcenter.R;

public class ErrorDialog extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogThemeNoTitle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.dialog_fragment_error, container);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        final TextView textView = (TextView) view.findViewById(R.id.error_text_view);

        Bundle arguments = this.getArguments();
        String error = arguments.getString("error");

        getDialog().setTitle("Error...");

        if(textView != null){

            textView.setText(error);
        }
    }
}
