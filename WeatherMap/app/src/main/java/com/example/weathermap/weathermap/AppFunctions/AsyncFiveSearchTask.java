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
import com.example.weathermap.weathermap.AppObjects.CustomViewFiveDays;
import com.example.weathermap.weathermap.AppObjects.CustomViewTimeData;
import com.example.weathermap.weathermap.AppObjects.CustomViewWeatherDesc;
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
    public String [] month_names;

    // UI Variables
    public TextView loading_text;
    public LinearLayout linear_00, linear_01, linear_02;

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
        month_names = res.getStringArray(R.array.month_names);
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

        // Create the main UI shell
        CustomViewFiveDays customViewFiveDays = new CustomViewFiveDays(eContext);
        customViewFiveDays.text_00.setText(cityFiveDaysObject.cityName);
        customViewFiveDays.text_01.setText(cityFiveDaysObject.cityCountry);
        customViewFiveDays.text_02.setText(eContext.getString(R.string.today_001) + " " +
                cityFiveDaysObject.coordLatitude + ", " + cityFiveDaysObject.coordLongitude);

        // Collect the dates of five
        int counter = 0;
        String[] date_keeper = {"", "", "", "", ""};
        date_keeper[0] = cityFiveDaysObject.weatherList.get(counter).dt_txt;
        date_keeper[0] = date_keeper[0].substring(0, date_keeper[0].indexOf(" ")).trim();

        // Set up date banner for Day 1
        customViewFiveDays.date_01.setText(month_names[Integer.parseInt(date_keeper[0].substring(5, 7))] + " " +
                date_keeper[0].substring(date_keeper[0].lastIndexOf("-") + 1) + ", " +
                date_keeper[0].substring(0, 4));
        for(int index = 0; index < cityFiveDaysObject.weatherList.size(); index++){
            // Seeks for a new date of next day
            if(!cityFiveDaysObject.weatherList.get(index).dt_txt.contains(date_keeper[counter]) &&
                    ((counter + 1) <= 4)) {
                date_keeper[counter + 1] = cityFiveDaysObject.weatherList.get(index).dt_txt;
                date_keeper[counter + 1] = date_keeper[counter + 1].substring(0,
                        date_keeper[counter + 1].indexOf(" ")).trim();
                counter++;
            }
            if(counter >= 4) break;
        }
        // Set up date banner for Day 2
        customViewFiveDays.date_02.setText(month_names[Integer.parseInt(date_keeper[1].substring(5, 7))] + " " +
                date_keeper[1].substring(date_keeper[1].lastIndexOf("-") + 1) + ", " +
                date_keeper[1].substring(0, 4));
        // Set up date banner for Day 3
        customViewFiveDays.date_03.setText(month_names[Integer.parseInt(date_keeper[2].substring(5, 7))] + " " +
                date_keeper[2].substring(date_keeper[2].lastIndexOf("-") + 1) + ", " +
                date_keeper[2].substring(0, 4));
        // Set up date banner for Day 4
        customViewFiveDays.date_04.setText(month_names[Integer.parseInt(date_keeper[3].substring(5, 7))] + " " +
                date_keeper[3].substring(date_keeper[3].lastIndexOf("-") + 1) + ", " +
                date_keeper[3].substring(0, 4));
        // Set up date banner for Day 5
        customViewFiveDays.date_05.setText(month_names[Integer.parseInt(date_keeper[4].substring(5, 7))] + " " +
                date_keeper[4].substring(date_keeper[4].lastIndexOf("-") + 1) + ", " +
                date_keeper[4].substring(0, 4));

        // To distribute the weather contents to rightful date columns
        for(int index = 0; index < cityFiveDaysObject.weatherList.size(); index++) {
            CustomViewTimeData customViewTimeData = new CustomViewTimeData(eContext);
            // Base of Temperature preference
            if(appJSONStorage.temperatureVar) {
                // To Fahrenheit degrees labeling
                customViewTimeData.text_01.setText(
                        cityFiveDaysObject.kelvinToFahrenheit(cityFiveDaysObject.weatherList.get(index).mainTemp)
                                + eContext.getString(R.string.measure_000a));
                customViewTimeData.text_02.setText(eContext.getString(R.string.fiveDays_003a) + " " +
                        cityFiveDaysObject.kelvinToFahrenheit(cityFiveDaysObject.weatherList.get(index).mainTemp_Min)
                        + eContext.getString(R.string.measure_000a));
                customViewTimeData.text_03.setText(eContext.getString(R.string.fiveDays_003b) + " " +
                        cityFiveDaysObject.kelvinToFahrenheit(cityFiveDaysObject.weatherList.get(index).mainTemp_Min)
                        + eContext.getString(R.string.measure_000a));
            } else {
                // To Celsius degrees labeling
                customViewTimeData.text_01.setText(
                        cityFiveDaysObject.kelvinToCelsius(cityFiveDaysObject.weatherList.get(index).mainTemp)
                                + eContext.getString(R.string.measure_000b));
                customViewTimeData.text_02.setText(eContext.getString(R.string.fiveDays_003a) + " " +
                        cityFiveDaysObject.kelvinToFahrenheit(cityFiveDaysObject.weatherList.get(index).mainTemp_Min)
                        + eContext.getString(R.string.measure_000b));
                customViewTimeData.text_03.setText(eContext.getString(R.string.fiveDays_003b) + " " +
                        cityFiveDaysObject.kelvinToFahrenheit(cityFiveDaysObject.weatherList.get(index).mainTemp_Min)
                        + eContext.getString(R.string.measure_000b));
            }
            customViewTimeData.text_00.setText(cityFiveDaysObject.weatherList.get(index).dt_txt.substring(
                    cityFiveDaysObject.weatherList.get(index).dt_txt.indexOf(" ")).trim()
                    + " " + eContext.getString(R.string.data_time));
            customViewTimeData.text_04.setText(eContext.getString(R.string.fiveDays_004a) + " "
                    + cityFiveDaysObject.weatherList.get(index).mainPressure + " "
                    + eContext.getString(R.string.measure_001a));
            customViewTimeData.text_05.setText(eContext.getString(R.string.fiveDays_004b) + " "
                    + cityFiveDaysObject.weatherList.get(index).mainHumidity
                    + eContext.getString(R.string.measure_002a));
            customViewTimeData.text_06.setText(eContext.getString(R.string.fiveDays_005a) + " "
                    + cityFiveDaysObject.weatherList.get(index).cloudsAll
                    + eContext.getString(R.string.measure_002a));
            customViewTimeData.text_07.setText(eContext.getString(R.string.fiveDays_005b) + " "
                    + cityFiveDaysObject.weatherList.get(index).windSpeed + " "
                    + eContext.getString(R.string.measure_003a));

            // Adding the Weather Icons and Description
            for(int index_0 = 0; index_0 < cityFiveDaysObject.weatherList.get(index).weatherMain.size(); index_0++){
                CustomViewWeatherDesc customViewWeatherDesc = new CustomViewWeatherDesc(eContext);
                customViewWeatherDesc.text_00.setText(
                        cityFiveDaysObject.weatherList.get(index).weatherMain.get(index_0));
                String splicer = cityFiveDaysObject.weatherList.get(index).weatherDescription.get(index_0);
                customViewWeatherDesc.text_01.setText(splicer.substring(0, 1).toUpperCase()
                                + splicer.substring(1));
                customViewWeatherDesc.image_00.setImageBitmap(
                        cityFiveDaysObject.weatherList.get(index).weatherIconFormed.get(index_0));
                customViewTimeData.weather_plate.addView(customViewWeatherDesc);
            }

            // For Day 1
            if(cityFiveDaysObject.weatherList.get(index).dt_txt.contains(date_keeper[0])) {
                customViewFiveDays.day_1.addView(customViewTimeData);
            }
            // For Day 2
            if(cityFiveDaysObject.weatherList.get(index).dt_txt.contains(date_keeper[1])) {
                customViewFiveDays.day_2.addView(customViewTimeData);
            }
            // For Day 3
            if(cityFiveDaysObject.weatherList.get(index).dt_txt.contains(date_keeper[2])) {
                customViewFiveDays.day_3.addView(customViewTimeData);
            }
            // For Day 4
            if(cityFiveDaysObject.weatherList.get(index).dt_txt.contains(date_keeper[3])) {
                customViewFiveDays.day_4.addView(customViewTimeData);
            }
            // For Day 5
            if(cityFiveDaysObject.weatherList.get(index).dt_txt.contains(date_keeper[4])) {
                customViewFiveDays.day_5.addView(customViewTimeData);
            }
        }

        linear_00.addView(customViewFiveDays);
    }
}
