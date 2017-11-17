package com.example.weathermap.weathermap.AppFunctions;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weathermap.weathermap.AppObjects.CityTodayObject;
import com.example.weathermap.weathermap.AppObjects.HttpObject;
import com.example.weathermap.weathermap.AppObjects.WeatherCustomView;
import com.example.weathermap.weathermap.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * The asynchronous task is used by the Today tab.
 * This is for loading the city data from the local JSON file.
 */

public class AsyncTodaySearchTask extends AsyncTask<String, String, String> {

    // Variables of the Asynchronous Task
    private String errorMessage = "";
    public Context eContext;
    public AppGraphics appGraphics;
    public AppJSONStorage appJSONStorage;
    public String urlJSONLink;
    public String urlImageLink;
    public CityTodayObject cityTodayObject;
    public Bitmap downloadedIcon;

    // UI Variables
    public TextView loading_text, text_00, text_01, text_02, text_03, text_04, text_05, text_06,
            text_07, text_08, text_09, text_10, text_11;
    public LinearLayout linear_00, linear_01, linear_02, linear_03;

    // Randomized Loading Quotes
    public String [] loadingMessages;

    // Constructor
    public AsyncTodaySearchTask(Context context, TextView loading_text, LinearLayout linear_00,
                                LinearLayout linear_01, LinearLayout linear_02,
                                TextView text_00, TextView text_01, TextView text_02, TextView text_03,
                                TextView text_04, TextView text_05, TextView text_06, TextView text_07,
                                TextView text_08, TextView text_09, TextView text_10, TextView text_11,
                                LinearLayout linear_03) {
        eContext = context;
        appGraphics = new AppGraphics(context);
        appJSONStorage = new AppJSONStorage(context, context.getString(R.string.user_file));
        appJSONStorage.dataRead();
        this.linear_00 = linear_00; // Data Linear
        this.linear_01 = linear_01; // Loading Linear
        this.linear_02 = linear_02; // Search Error Linear
        this.loading_text = loading_text;

        // Data Panel UI
        this.text_00 = text_00;
        this.text_01 = text_01;
        this.text_02 = text_02;
        this.text_03 = text_03;
        this.text_04 = text_04;
        this.text_05 = text_05;
        this.text_06 = text_06;
        this.text_07 = text_07;
        this.text_08 = text_08;
        this.text_09 = text_09;
        this.text_10 = text_10;
        this.text_11 = text_11;
        this.linear_03 = linear_03;

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
        urlJSONLink = "http://api.openweathermap.org/data/2.5/weather?q=" + params[0] +
                ",us&APPID=" + eContext.getString(R.string.weathermap_owm_api_key);
        urlImageLink = "http://openweathermap.org/img/w/";
        cityTodayObject = new CityTodayObject();

        // Initiates JSON source
        HttpObject httpObject = new HttpObject();
        String jsonString = httpObject.makeServiceCall(urlJSONLink);

        // Load the city list data
        if(jsonString != null) {
            try {
                // Obtain as JSON object
                JSONObject jsonObject = new JSONObject(jsonString);

                // Get values from the 'coord' node
                JSONObject coord = jsonObject.getJSONObject("coord");
                cityTodayObject.coordLongitude = coord.getDouble("lon");
                cityTodayObject.coordLatitude = coord.getDouble("lat");

                // Getting JSON Array of 'weather'
                JSONArray weather = jsonObject.getJSONArray("weather");
                for(int index = 0; index < weather.length(); index++) {
                    // Obtaining the weather data from each node
                    JSONObject weatherData = weather.getJSONObject(index);
                    cityTodayObject.weatherID.add(weatherData.getInt("id"));
                    cityTodayObject.weatherMain.add(weatherData.getString("main"));
                    cityTodayObject.weatherDescription.add(weatherData.getString("description"));
                    cityTodayObject.weatherIcon.add(weatherData.getString("icon"));
                }

                // Get values from the 'main' node
                JSONObject main = jsonObject.getJSONObject("main");
                cityTodayObject.mainTemp = main.getDouble("temp");
                cityTodayObject.mainPressure = main.getDouble("pressure");
                cityTodayObject.mainHumidity = main.getInt("humidity");
                cityTodayObject.mainTemp_Min = main.getDouble("temp_min");
                cityTodayObject.mainTemp_Max = main.getDouble("temp_max");

                // Get values from the 'wind' node
                JSONObject wind = jsonObject.getJSONObject("wind");
                cityTodayObject.windSpeed = wind.getDouble("speed");

                // Get values from the 'clouds' node
                JSONObject clouds = jsonObject.getJSONObject("clouds");
                cityTodayObject.cloudsAll = clouds.getDouble("all");

                // Get the 'dt' object
                cityTodayObject.dt = jsonObject.getInt("dt");

                // Get values from the 'sys' node
                JSONObject sys = jsonObject.getJSONObject("sys");
                cityTodayObject.sysMessage = sys.getDouble("message");
                cityTodayObject.sysCountry = sys.getString("country");
                cityTodayObject.sysSunrise = sys.getInt("sunrise");
                cityTodayObject.sysSunset = sys.getInt("sunset");

                // Get the 'id' object
                cityTodayObject.cityID = jsonObject.getInt("id");

                // Get the 'name' object
                cityTodayObject.cityName = jsonObject.getString("name");

                // Get the 'cod' object
                cityTodayObject.errorCode = jsonObject.getInt("cod");

                // Download the weather icons from the URL, and resize it
                for(int index = 0; index < cityTodayObject.weatherIcon.size(); index++){
                    downloadedIcon = appGraphics.getBitmapFromURL(urlImageLink +
                            cityTodayObject.weatherIcon.get(index) + ".png");
                    downloadedIcon = appGraphics.getResizedBitmap(
                            downloadedIcon, 100, 100);
                    cityTodayObject.weatherIconFormed.add(downloadedIcon);
                }

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
        if((cityTodayObject.errorCode != 200) ||
                !(cityTodayObject.sysCountry.toLowerCase().equals("us"))) {
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
            appJSONStorage.lastCitySearched = cityTodayObject.cityName;
            appJSONStorage.lastCityID = cityTodayObject.cityID;
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
    public CityTodayObject getCityTodayObject() {
        return cityTodayObject;
    }

    // Fill the data table panel up
    public void populateDataTable() {
        text_00.setText(cityTodayObject.cityName);
        text_01.setText(cityTodayObject.sysCountry);
        text_02.setText(eContext.getString(R.string.today_001) + " " +
                cityTodayObject.coordLatitude + ", " + cityTodayObject.coordLongitude);
        text_06.setText(eContext.getString(R.string.today_004b) + " " +
                cityTodayObject.mainPressure + " hPa");
        text_07.setText(eContext.getString(R.string.today_005b) + " " +
                cityTodayObject.mainHumidity + "%");
        text_08.setText(eContext.getString(R.string.today_006a) + " " +
                cityTodayObject.windSpeed + " meter/sec");
        text_09.setText(eContext.getString(R.string.today_006b) + " " +
                cityTodayObject.cloudsAll + "%");
        text_10.setText(eContext.getString(R.string.today_007a) + " " +
                cityTodayObject.epochToDate(cityTodayObject.sysSunrise));
        text_11.setText(eContext.getString(R.string.today_007b) + " " +
                cityTodayObject.epochToDate(cityTodayObject.sysSunset));
        linear_03.removeAllViews();

        // Base of Temperature preference
        if(appJSONStorage.temperatureVar) {
            // To Fahrenheit degrees labeling
            text_03.setText(eContext.getString(R.string.today_003) + " " +
                    cityTodayObject.kelvinToFahrenheit(cityTodayObject.mainTemp) +
                    eContext.getString(R.string.options_on_a));
            text_04.setText(eContext.getString(R.string.today_004a) + " " +
                    cityTodayObject.kelvinToFahrenheit(cityTodayObject.mainTemp_Min) +
                    eContext.getString(R.string.options_on_a));
            text_05.setText(eContext.getString(R.string.today_005a) + " " +
                    cityTodayObject.kelvinToFahrenheit(cityTodayObject.mainTemp_Max) +
                    eContext.getString(R.string.options_on_a));
        } else {
            // To Celsius degrees labeling
            text_03.setText(eContext.getString(R.string.today_003) + " " +
                    cityTodayObject.kelvinToCelsius(cityTodayObject.mainTemp) +
                    eContext.getString(R.string.options_off_a));
            text_04.setText(eContext.getString(R.string.today_004a) + " " +
                    cityTodayObject.kelvinToCelsius(cityTodayObject.mainTemp_Min) +
                    eContext.getString(R.string.options_off_a));
            text_05.setText(eContext.getString(R.string.today_005a) + " " +
                    cityTodayObject.kelvinToCelsius(cityTodayObject.mainTemp_Max) +
                    eContext.getString(R.string.options_off_a));
        }

        // Populate the forecast section
        for(int index = 0; index < cityTodayObject.weatherID.size(); index++) {

            // For the data of the Forecast
            WeatherCustomView weatherCustomView = new WeatherCustomView(eContext);
            weatherCustomView.iconPicture.setImageBitmap(cityTodayObject.weatherIconFormed.get(index));
            weatherCustomView.text_00.setText(cityTodayObject.weatherMain.get(index));
            String modDescription = cityTodayObject.weatherDescription.get(index);
            weatherCustomView.text_01.setText(modDescription.substring(0, 1).toUpperCase()
                    + modDescription.substring(1));
            linear_03.addView(weatherCustomView);
        }
    }
}
