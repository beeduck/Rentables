package com.rentables.testcenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

public class AdvancedSearchDialog extends DialogFragment {

    RadioButton[] radioButtons;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogThemeNoTitle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.dialog_fragment_advanced_search, container);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        setRadioButtonArray();
        setListenersForRadioButtons();

    }

    public void setRadioButtonArray(){

        ViewGroup parent1 = (ViewGroup)getView().findViewById(R.id.radio_button_row_1);
        ViewGroup parent2 = (ViewGroup)getView().findViewById(R.id.radio_button_row_2);

        int childrenAtRow1 = parent1.getChildCount();
        int childrenAtRow2 = parent2.getChildCount();

        radioButtons = new RadioButton[childrenAtRow1 + childrenAtRow2];

        for(int i = 0; i < radioButtons.length; i++){

            if(i < childrenAtRow1){
                radioButtons[i] = (RadioButton) parent1.getChildAt(i);
            }else{
                radioButtons[i] = (RadioButton) parent2.getChildAt(i - childrenAtRow1);
            }
        }
    }

    public void checkRadioButtons(View view){

        for(int i = 0; i < radioButtons.length; i++){

            if(view != radioButtons[i]){

                radioButtons[i].setChecked(false);
            }
        }
    }

    public void setListenerForSearchButton(){

        View currentView = getView();

        Button button = (Button) currentView.findViewById(R.id.button_advanced_search);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    public void setListenersForRadioButtons(){

        View currentView = getView();

        RadioButton rb1 = (RadioButton) currentView.findViewById(R.id.radio_button_days);
        RadioButton rb2 = (RadioButton) currentView.findViewById(R.id.radio_button_hours);
        RadioButton rb3 = (RadioButton) currentView.findViewById(R.id.radio_button_months);
        RadioButton rb4 = (RadioButton) currentView.findViewById(R.id.radio_button_weeks);

        rb1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRadioButtons(v);
            }
        });

        rb2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRadioButtons(v);
            }
        });

        rb3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRadioButtons(v);
            }
        });

        rb4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRadioButtons(v);
            }
        });
    }
}
