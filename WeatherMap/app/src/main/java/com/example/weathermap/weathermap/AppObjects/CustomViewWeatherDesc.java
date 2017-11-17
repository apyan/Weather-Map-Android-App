package com.example.weathermap.weathermap.AppObjects;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weathermap.weathermap.R;

/**
 * A custom view for each weather description
 */

public class CustomViewWeatherDesc  extends LinearLayout {

    // Variables for the Custom View
    public View parentView;
    public ImageView image_00;
    public TextView text_00, text_01;

    // Constructor
    public CustomViewWeatherDesc(Context context) {
        super(context);
        init(context);
    }

    public CustomViewWeatherDesc(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        parentView = inflate(context, R.layout.custom_weather_desc, this);

        // UI elements
        text_00 = (TextView) parentView.findViewById(R.id.desc_main);
        text_01 = (TextView) parentView.findViewById(R.id.desc_desc);
        image_00 = (ImageView) parentView.findViewById(R.id.image_icon);
    }
}
