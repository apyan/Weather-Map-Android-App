package com.example.weathermap.weathermap.AppObjects;

import android.graphics.Bitmap;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Date;

/**
 * An object used for storing the city's data.
 */

public class CityTodayObject {

    // Variables of the object
    public double coordLongitude;
    public double coordLatitude;
    public ArrayList<Integer> weatherID;
    public ArrayList<String> weatherMain;
    public ArrayList<String> weatherDescription;
    public ArrayList<String> weatherIcon;
    public double mainTemp;
    public double mainPressure;
    public int mainHumidity;
    public double mainTemp_Min;
    public double mainTemp_Max;
    public double windSpeed;
    public double cloudsAll;
    public int dt;
    public double sysMessage;
    public String sysCountry;
    public int sysSunrise;
    public int sysSunset;
    public int cityID;
    public String cityName;
    public int errorCode;

    public ArrayList<Bitmap> weatherIconFormed;

    // Constructor
    public CityTodayObject() {
        weatherID = new ArrayList<>();
        weatherMain = new ArrayList<>();
        weatherDescription = new ArrayList<>();
        weatherIcon = new ArrayList<>();
        weatherIconFormed = new ArrayList<>();
    }

    // Returns the results for testing purposes
    public String viewVariables(){
        return coordLongitude + ", " + coordLatitude + "\n" +
                errorCode + ": " + cityName + "," + sysCountry + "\n" +
                cityID + ": " + weatherDescription;
    }

    // Convert Kelvin to Fahrenheit
    public double kelvinToFahrenheit(double kDegrees) {
        double fah = (((kDegrees - 273) * (9/5)) + 32);
        // For two decimal places
        fah = Math.round(fah * 100.0) / 100.0;
        return fah;
    }

    // Convert Epoch Number to Human Time
    public String epochToDate(int epochVal) {
        String epochString = "" + epochVal;
        long epoch = Long.parseLong(epochString);
        Date expiry = new Date(epoch * 1000);
        return expiry.toString();
    }
}
