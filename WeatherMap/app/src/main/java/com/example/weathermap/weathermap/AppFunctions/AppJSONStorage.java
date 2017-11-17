package com.example.weathermap.weathermap.AppFunctions;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class is used for writing and saving data
 * of the Android application.
 */

public class AppJSONStorage {

    // Variables
    public Context context;
    public String filename;

    // File Data Variables
    public String lastCitySearched = "";
    public int lastCityID = 0;
    public boolean titleScreenOn = true;
    public boolean temperatureVar = true;

    // Constructor
    // Ex. "user-data.json"
    public AppJSONStorage(Context eContext, String name){
        context = eContext;
        filename = name;
    }

    // Checks for the file existence
    public boolean fileExists() {
        File file = new File(context.getFilesDir(), filename);
        if(file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    // Initializes a new file
    public void initializeFile() {
        File file = new File(context.getFilesDir(), filename);
        if(!file.exists()) {
            dataWrite();
        }
    }

    // Read data from the file
    public void dataRead() {
        // Load data
        String jsonString = loadData();

        try {
            // Obtain as JSON object
            JSONObject jsonObject = new JSONObject(jsonString);
            lastCitySearched = jsonObject.getString("lastCitySearched");
            lastCityID = jsonObject.getInt("lastCityID");
            titleScreenOn = jsonObject.getBoolean("titleScreenOn");
            temperatureVar = jsonObject.getBoolean("temperatureVar");

        } catch (final JSONException e) {
            e.getMessage();
        }

    }

    // Load data from the file as a String
    public String loadData() {
        // Variables
        String line;
        String combined = "";
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(new File(context.getFilesDir(), filename)));
            // Read every line from the file
            while ((line = in.readLine()) != null) {
                // Reading data
                combined = combined + line;
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return combined;
    }

    // Write data to the file
    public void dataWrite() {
        // Variables
        String writer = "{";

        // Begin the JSON file construction
        // For the last city name sought
        writer = writer + "\r\n\"lastCitySearched\" : \"" + lastCitySearched + "\"";

        // For the last city ID sought
        writer = writer + ",\r\n\"lastCityID\" : " + lastCityID;

        // For the Title Screen option
        writer = writer + ",\r\n\"titleScreenOn\" : " + titleScreenOn;

        // For the Temperature Variation option
        writer = writer + ",\r\n\"temperatureVar\" : " + temperatureVar;

        // End the JSON file construction
        writer = writer + "}";

        try {
            FileWriter out = new FileWriter(new File(context.getFilesDir(), filename));
            out.write(writer);
            out.close();
        } catch (IOException e) {
        }
    }

}
