package com.example.weathermap.weathermap.AppObjects;

/**
 * An object used for storing the city's data.
 */

public class CityLocationObject {

    // Variables of the object
    public int cityID;
    public String cityName;
    public String countryName;
    public String combinedName;

    // Constructor
    public CityLocationObject(int cityID, String cityName, String countryName) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.countryName = countryName;
        this.combinedName = cityName + ", " + countryName;
    }

}
