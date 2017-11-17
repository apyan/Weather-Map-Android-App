package com.example.weathermap.weathermap.AppObjects;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weathermap.weathermap.R;

/**
 * A custom view for the forecast label for Today
 */

public class WeatherCustomView extends LinearLayout {

    // Variables for the Custom View
    public View parentView;
    public ImageView iconPicture;
    public TextView text_00, text_01;

    // Constructor
    public WeatherCustomView(Context context) {
        super(context);
        init(context);
    }

    public WeatherCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        parentView = inflate(context, R.layout.custom_weather_data, this);

        // UI elements
        iconPicture = (ImageView) parentView.findViewById(R.id.data_icon);
        text_00 = (TextView) parentView.findViewById(R.id.text_00);
        text_01 = (TextView) parentView.findViewById(R.id.text_01);
    }
}
