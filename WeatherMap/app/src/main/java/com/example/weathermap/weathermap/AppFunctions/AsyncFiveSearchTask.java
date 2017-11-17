package com.example.weathermap.weathermap.AppFunctions;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weathermap.weathermap.R;

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

        return errorMessage;
    }

    @Override
    protected void onPostExecute(String args) {

    }

    // Randomizes a loading message
    public String randomMessage() {
        Random rand = new Random();
        int randNum = rand.nextInt(loadingMessages.length);
        String messageR = loadingMessages[randNum];
        return messageR;
    }

    // Fill the data table panel up
    public void populateDataTable() {

    }
}
