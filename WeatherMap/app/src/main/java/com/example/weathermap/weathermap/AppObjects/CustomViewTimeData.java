package com.example.weathermap.weathermap.AppObjects;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weathermap.weathermap.R;

/**
 * A custom view for each time socket
 */

public class CustomViewTimeData extends LinearLayout {

    // Variables for the Custom View
    public View parentView;
    public TextView text_00, text_01, text_02, text_03, text_04, text_05, text_06, text_07;
    public LinearLayout weather_plate;

    // Constructor
    public CustomViewTimeData(Context context) {
        super(context);
        init(context);
    }

    public CustomViewTimeData(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        parentView = inflate(context, R.layout.custom_time_data, this);

        // UI elements
        text_00 = (TextView) parentView.findViewById(R.id.time_title);
        text_01 = (TextView) parentView.findViewById(R.id.time_temp);
        text_02 = (TextView) parentView.findViewById(R.id.time_minTemp);
        text_03 = (TextView) parentView.findViewById(R.id.time_maxTemp);
        text_04 = (TextView) parentView.findViewById(R.id.time_cloud);
        text_05 = (TextView) parentView.findViewById(R.id.time_pressure);
        text_06 = (TextView) parentView.findViewById(R.id.time_humidity);
        text_07 = (TextView) parentView.findViewById(R.id.time_wind);
        weather_plate = (LinearLayout) parentView.findViewById(R.id.weather_display);
    }
}
