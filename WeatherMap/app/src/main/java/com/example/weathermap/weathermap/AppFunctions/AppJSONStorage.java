package com.example.weathermap.weathermap.AppFunctions;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is used for writing and saving data
 * of the Android application.
 */

public class AppJSONStorage {

    // Variables
    Context context;
    public String filename;

    // File Data Variables
    public String lastCityName = "none";
    public int lastCityID = 0;
    public boolean notifyOn = false;

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

    }

    // Write data to the file
    public void dataWrite() {
        // Variables
        String writer = "{";

        // Begin the JSON file construction
        // For the last city name sought
        writer = writer + "\"lastCityName\" : " + lastCityName;

        // For the last city ID sought
        writer = writer + ", \"lastCityID\" : " + lastCityID;

        // End the JSON file construction
        writer = writer + "}";

        try {
            FileWriter out = new FileWriter(new File(context.getFilesDir(), filename));
            out.write(writer);
            out.close();
        } catch (IOException e) {
            //Logger.logError(TAG, e);
        }
    }

}
