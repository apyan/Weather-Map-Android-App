package com.example.weathermap.weathermap.AppObjects;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * A node class to hold the weather data for the timeslot
 * for the 5-day forecast.
 */

public class WeatherDataNode {

    // Variables of the object
    public double mainTemp;
    public double mainTemp_Min;
    public double mainTemp_Max;
    public double mainPressure;
    public int mainHumidity;
    public ArrayList<String> weatherMain;
    public ArrayList<String> weatherDescription;
    public ArrayList<String> weatherIcon;
    public int cloudsAll;
    public double windSpeed;
    public String dt_txt;

    public ArrayList<Bitmap> weatherIconFormed;

    // Constructor
    public WeatherDataNode() {
        weatherMain = new ArrayList<>();
        weatherDescription = new ArrayList<>();
        weatherIcon = new ArrayList<>();
        weatherIconFormed = new ArrayList<>();
    }
}
