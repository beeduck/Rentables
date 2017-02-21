package com.rentables.testcenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ListingsViewHolder extends RecyclerView.ViewHolder {

    private View currentView;

    public ListingsViewHolder(View itemView) {

        super(itemView);

        currentView = itemView;
    }

    public View getCurrentView(){

        return currentView;
    }
}
