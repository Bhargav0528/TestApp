package com.example.bhargav.testapp;

import android.view.View;

/**
 * Created by BhargavBV on 31-08-2017.
 */
public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
