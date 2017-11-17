package com.example.weathermap.weathermap.AppObjects;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weathermap.weathermap.R;

/**
 * A custom view for the 5-Day tab overall
 */

public class CustomViewFiveDays extends LinearLayout {

    // Variables for the Custom View
    public View parentView;
    public TextView text_00, text_01, text_02;
    public TextView date_01, date_02, date_03, date_04, date_05;
    public LinearLayout day_1, day_2, day_3, day_4, day_5;

    // Constructor
    public CustomViewFiveDays(Context context) {
        super(context);
        init(context);
    }

    public CustomViewFiveDays(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        parentView = inflate(context, R.layout.custom_five_days_data, this);

        // UI elements
        text_00 = (TextView) parentView.findViewById(R.id.data_city);
        text_01 = (TextView) parentView.findViewById(R.id.data_country);
        text_02 = (TextView) parentView.findViewById(R.id.data_coordinates);
        date_01 = (TextView) parentView.findViewById(R.id.title_01);
        date_02 = (TextView) parentView.findViewById(R.id.title_02);
        date_03 = (TextView) parentView.findViewById(R.id.title_03);
        date_04 = (TextView) parentView.findViewById(R.id.title_04);
        date_05 = (TextView) parentView.findViewById(R.id.title_05);
        day_1 = (LinearLayout) parentView.findViewById(R.id.report_01);
        day_2 = (LinearLayout) parentView.findViewById(R.id.report_02);
        day_3 = (LinearLayout) parentView.findViewById(R.id.report_03);
        day_4 = (LinearLayout) parentView.findViewById(R.id.report_04);
        day_5 = (LinearLayout) parentView.findViewById(R.id.report_05);
    }
}
