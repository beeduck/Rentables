package com.rentables.testcenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Asad on 3/30/2017.
 */

public class RentRequestViewHolder extends RecyclerView.ViewHolder {

    private View currentView;

    public RentRequestViewHolder(View itemView) {
        super(itemView);
        currentView = itemView;
    }

    public View getCurrentView() {
        return currentView;
    }
}
