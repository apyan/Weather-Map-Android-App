package com.example.weathermap.weathermap.AppFunctions;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weathermap.weathermap.AppObjects.CityFiveDaysObject;
import com.example.weathermap.weathermap.AppObjects.HttpObject;
import com.example.weathermap.weathermap.AppObjects.WeatherDataNode;
import com.example.weathermap.weathermap.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * The asynchronous task is used by the 5-Days tab.
 * This is for loading the city data from the local JSON file.
 */

public class AsyncFiveSearchTask extends AsyncTask<String, String, String> {

    // Variables of the Asynchronous Task
    private String errorMessage = "";
    public Context eContext;
    public AppGraphics appGraphics;
    public AppJSONStorage appJSONStorage;
    public String urlJSONLink;
    public String urlImageLink;
    public CityFiveDaysObject cityFiveDaysObject;
    public Bitmap downloadedIcon;

    // UI Variables
    public TextView loading_text;
    public LinearLayout linear_00, linear_01, linear_02, linear_03;

    // Randomized Loading Quotes
    public String [] loadingMessages;

    // Constructor
    public AsyncFiveSearchTask(Context context, TextView loading_text, LinearLayout linear_00,
                               LinearLayout linear_01, LinearLayout linear_02) {
        eContext = context;
        appGraphics = new AppGraphics(context);
        appJSONStorage = new AppJSONStorage(context, context.getString(R.string.user_file));
        appJSONStorage.dataRead();
        this.linear_00 = linear_00; // Data Linear
        this.linear_01 = linear_01; // Loading Linear
        this.linear_02 = linear_02; // Search Error Linear
        this.loading_text = loading_text;

        // Load Messages
        Resources res = context.getResources();
        loadingMessages = res.getStringArray(R.array.loading_messages);
    }

    @Override
    protected void onPreExecute() {

        // Outputs a loading message
        this.loading_text.setText(randomMessage());

        // Shows the proper UI
        this.linear_00.setVisibility(View.GONE);
        this.linear_01.setVisibility(View.VISIBLE);
        this.linear_02.setVisibility(View.GONE);
    }

    @Override
    protected String doInBackground(String... params) {

        // Form the URL for the JSON source
        urlJSONLink = "http://api.openweathermap.org/data/2.5/forecast?q=" + params[0] +
                ",us&APPID=" + eContext.getString(R.string.weathermap_owm_api_key);
        urlImageLink = "http://openweathermap.org/img/w/";
        cityFiveDaysObject = new CityFiveDaysObject();

        // Initiates JSON source
        HttpObject httpObject = new HttpObject();
        String jsonString = httpObject.makeServiceCall(urlJSONLink);

        // Load the city list data
        if(jsonString != null) {
            try {
                // Obtain as JSON object
                JSONObject jsonObject = new JSONObject(jsonString);

                // Obtain the 'errorCode' from 'cod'
                cityFiveDaysObject.errorCode = jsonObject.getString("cod");

                // Getting JSON Array of 'list'
                JSONArray list = jsonObject.getJSONArray("list");
                for(int index = 0; index < list.length(); index++) {
                    // Obtaining the weather data from each node
                    WeatherDataNode weatherDataNode = new WeatherDataNode();
                    JSONObject weatherData = list.getJSONObject(index);

                    // Get from 'main' node
                    JSONObject main = weatherData.getJSONObject("main");
                    weatherDataNode.mainTemp = main.getDouble("temp");
                    weatherDataNode.mainTemp_Min = main.getDouble("temp_min");
                    weatherDataNode.mainTemp_Max = main.getDouble("temp_max");
                    weatherDataNode.mainPressure = main.getDouble("pressure");
                    weatherDataNode.mainHumidity = main.getInt("humidity");

                    // Getting JSON Array of 'weather'
                    JSONArray weather = weatherData.getJSONArray("weather");
                    for(int index_0 = 0; index_0  < weather.length(); index_0++) {
                        // Obtaining the weather data from each node
                        JSONObject weatherList = weather.getJSONObject(index_0);
                        weatherDataNode.weatherMain.add(weatherList.getString("main"));
                        weatherDataNode.weatherDescription.add(weatherList.getString("description"));
                        weatherDataNode.weatherIcon.add(weatherList.getString("icon"));

                        // Download the image icon from source
                        downloadedIcon = appGraphics.getBitmapFromURL(urlImageLink +
                                weatherDataNode.weatherIcon.get(index_0) + ".png");
                        downloadedIcon = appGraphics.getResizedBitmap(
                                downloadedIcon, 100, 100);
                        weatherDataNode.weatherIconFormed.add(downloadedIcon);
                    }

                    // Get from 'clouds' node
                    JSONObject clouds = weatherData.getJSONObject("clouds");
                    weatherDataNode.cloudsAll = clouds.getInt("all");


                    // Get from 'wind' node
                    JSONObject wind = weatherData.getJSONObject("wind");
                    weatherDataNode.windSpeed = wind.getDouble("speed");

                    // Get from 'dt_txt'
                    weatherDataNode.dt_txt = weatherData.getString("dt_txt");

                    // Record in ArrayList
                    cityFiveDaysObject.weatherList.add(weatherDataNode);
                }

                // Get from 'city' node
                JSONObject city = jsonObject.getJSONObject("city");
                cityFiveDaysObject.cityId = city.getInt("id");
                cityFiveDaysObject.cityName = city.getString("name");

                // Get from 'coord' node
                JSONObject coord = city.getJSONObject("coord");
                cityFiveDaysObject.coordLatitude = coord.getDouble("lat");
                cityFiveDaysObject.coordLongitude = coord.getDouble("lon");

                cityFiveDaysObject.cityCountry = city.getString("country");

            } catch (JSONException e) {
                errorMessage = e.getMessage();
            }
        }

        return errorMessage;
    }

    @Override
    protected void onPostExecute(String args) {

        // After the task is done, shows the correct UI
        // Checks to see if the search was valid
        if(!(cityFiveDaysObject.errorCode.equals("200")) ||
                !(cityFiveDaysObject.cityCountry.toLowerCase().equals("us"))) {
            // Invalid Search
            this.linear_00.setVisibility(View.GONE);
            this.linear_01.setVisibility(View.GONE);
            this.linear_02.setVisibility(View.VISIBLE);
        } else {
            // Valid Search
            this.linear_00.setVisibility(View.VISIBLE);
            this.linear_01.setVisibility(View.GONE);
            this.linear_02.setVisibility(View.GONE);

            // Save as last city searched in user file
            appJSONStorage.lastCitySearched = cityFiveDaysObject.cityName;
            appJSONStorage.lastCityID = cityFiveDaysObject.cityId;
            appJSONStorage.dataWrite();

            populateDataTable();
        }
    }

    // Randomizes a loading message
    public String randomMessage() {
        Random rand = new Random();
        int randNum = rand.nextInt(loadingMessages.length);
        String messageR = loadingMessages[randNum];
        return messageR;
    }

    // Return the City Information node
    public CityFiveDaysObject getCityFiveDaysObject() {
        return cityFiveDaysObject;
    }

    // Fill the data table panel up
    public void populateDataTable() {

    }
}
