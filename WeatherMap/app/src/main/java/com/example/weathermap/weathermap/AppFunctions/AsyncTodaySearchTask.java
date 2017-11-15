package com.example.weathermap.weathermap.AppFunctions;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weathermap.weathermap.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * The asynchronous task is used by the splash screen.
 * This is for loading the city data from the local JSON file.
 */

public class AsyncTodaySearchTask extends AsyncTask<String, String, String> {

    // Variables of the Asynchronous Task
    private String errorMessage = "";
    public Context eContext;
    public AppGraphics appGraphics;
    public boolean successResult = false;
    public String urlJSONLink;

    // UI Variables
    public TextView loading_text;
    public LinearLayout linear_00, linear_01, linear_02, linear_03;

    // Randomized Loading Quotes
    public String [] loadingMessages;

    // Constructor
    public AsyncTodaySearchTask(Context context, TextView loading_text, LinearLayout linear_00,
                                LinearLayout linear_01, LinearLayout linear_02, LinearLayout linear_03) {
        eContext = context;
        appGraphics = new AppGraphics(context);
        this.linear_00 = linear_00;
        this.linear_01 = linear_01;
        this.linear_02 = linear_02;
        this.linear_03 = linear_03;
        this.loading_text = loading_text;

        // Load Messages
        Resources res = context.getResources();
        loadingMessages = res.getStringArray(R.array.loading_messages);
    }

    @Override
    protected void onPreExecute() {

        // Outputs a loading message
        this.loading_text.setText(randomMessage());
        this.linear_01.setVisibility(View.GONE);
    }

    @Override
    protected String doInBackground(String... params) {

        // Form the URL for the JSON source
        urlJSONLink = "http://api.openweathermap.org/data/2.5/weather?q=" + params[0] +
                ",us&APPID=" + eContext.getString(R.string.weathermap_owm_api_key);

        // Load the city list data
        try {
            String jsonFileContent = "us_city_list.json";
            JSONArray jsonArray = new JSONArray(jsonFileContent);
            for(int index = 0; index < jsonArray.length(); index++) {
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                String country = jsonObject.getString("country");
            }
        } catch (JSONException e) {
            errorMessage = e.getMessage();
        }

        return errorMessage;
    }

    @Override
    protected void onPostExecute(String args) {

        // After the task is done, eliminate the progress bar
        // and loading message
        this.linear_00.setVisibility(View.GONE);
        this.linear_01.setVisibility(View.VISIBLE);
    }

    // Randomizes a loading message
    public String randomMessage(){
        Random rand = new Random();
        int randNum = rand.nextInt(loadingMessages.length);
        String messageR = loadingMessages[randNum];
        return messageR;
    }
}
