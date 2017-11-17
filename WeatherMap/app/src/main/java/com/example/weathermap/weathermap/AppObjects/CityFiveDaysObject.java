package com.example.weathermap.weathermap.AppObjects;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;

/**
 * An object used for storing the city's data for the 5-days.
 */

public class CityFiveDaysObject {

    // Variables of the object
    public String errorCode;
    public ArrayList<WeatherDataNode> weatherList;
    public int cityId;
    public String cityName;
    public double coordLatitude;
    public double coordLongitude;
    public String cityCountry;

    // Constructor
    public CityFiveDaysObject() {
        weatherList = new ArrayList<>();
    }

    // Convert Kelvin to Fahrenheit
    public double kelvinToFahrenheit(double kDegrees) {
        double fah = (((kDegrees - 273.15) * (9/5)) + 32);
        // For two decimal places
        fah = Math.round(fah * 100.0) / 100.0;
        return fah;
    }

    // Convert Kelvin to Celsius
    public double kelvinToCelsius(double kDegrees) {
        double cel = (kDegrees - 273.15);
        // For two decimal places
        cel = Math.round(cel * 100.0) / 100.0;
        return cel;
    }

    // Convert Epoch Number to Human Time
    public String epochToDate(int epochVal) {
        String epochString = "" + epochVal;
        long epoch = Long.parseLong(epochString);
        Date expiry = new Date(epoch * 1000);
        return expiry.toString();
    }

}
