package com.example.weathermap.weathermap.AppFunctions;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weathermap.weathermap.AppObjects.CityLocationObject;
import com.example.weathermap.weathermap.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * The asynchronous task is used by the splash screen.
 * This is for loading the city data from the local JSON file.
 */

public class AsyncSearchTask extends AsyncTask<String, String, String> {

    // Variables of the Asynchronous Task
    private String errorMessage = "";
    public Context eContext;
    AppGraphics appGraphics;
    ArrayList<CityLocationObject> cityListing;
    ArrayList<String> cityNames;

    // UI Variables
    ProgressBar progressBar;
    TextView textView;
    Activity activity;
    LinearLayout linear_00, linear_01;

    // Randomized Loading Quotes
    String [] loadingMessages;

    // Constructor
    public AsyncSearchTask(Context context, Activity activity, ProgressBar progressBar,
                           TextView textView, LinearLayout linear_00, LinearLayout linear_01) {
        eContext = context;
        appGraphics = new AppGraphics(context);
        this.progressBar = progressBar;
        this.textView = textView;
        this.activity = activity;
        this.linear_00 = linear_00;
        this.linear_01 = linear_01;

        // Load Messages
        Resources res = context.getResources();
        loadingMessages = res.getStringArray(R.array.loading_messages);
    }

    @Override
    protected void onPreExecute() {

        // Outputs a loading message
        this.textView.setText(randomMessage());
        this.linear_01.setVisibility(View.GONE);
    }

    @Override
    protected String doInBackground(String... params) {

        // Load the city list data
        cityListing = new ArrayList<CityLocationObject>();
        cityNames = new ArrayList<String>();
        try {
            String jsonFileContent = readFile("us_city_list.json");
            JSONArray jsonArray = new JSONArray(jsonFileContent);
            for(int index = 0; index < jsonArray.length(); index++) {
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                String country = jsonObject.getString("country");
                cityListing.add(new CityLocationObject(id, name, country));
                cityNames.add(name);
            }
        } catch (IOException e) {
            errorMessage = e.getMessage();
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

        Toast.makeText(eContext, "Length: " + cityListing.size(), Toast.LENGTH_SHORT).show();
        Toast.makeText(eContext, "First: " + cityListing.get(0).cityName + "\nID: "
                + cityListing.get(0).cityID, Toast.LENGTH_SHORT).show();
    }

    // Randomizes a loading message
    public String randomMessage(){
        Random rand = new Random();
        int randNum = rand.nextInt(loadingMessages.length);
        String messageR = loadingMessages[randNum];
        return messageR;
    }

    // Read every line of code from a given file
    public String readFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(this.activity.getAssets().open(fileName),"UTF-8"));
        Log.d("READER", "File Size: " + this.activity.getAssets().open(fileName).available());
        String combined = "";
        String line;

        // Read every line from the file
        while((line = reader.readLine()) != null) {
            combined = combined + line;
        }
        return combined;
    }

    // Returns the city listings
    public ArrayList<CityLocationObject> getCityListing(){
        return cityListing;
    }

    // Returns the city names
    public ArrayList<String> getCityNames(){
        return cityNames;
    }
}
