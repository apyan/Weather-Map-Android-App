package com.example.weathermap.weathermap.AppFunctions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.weathermap.weathermap.R;

import java.util.Random;

/**
 * Created by apyan on 11/11/17.
 */

public class AsyncSplashScreen extends AsyncTask<String, String, String> {

    // Variables of the Asynchronous Task
    private String errorMessage = "";
    Context eContext;
    ProgressDialog progressDialog;
    ImageView image_00;
    ProgressBar progressBar;
    AppGraphics appGraphics;
    Bitmap imageDownload;

    // Randomized Loading Quotes
    String [] loadingMessages;

    // Constructor
    public AsyncSplashScreen(Context context, ImageView imageView, ProgressBar imageProgress) {
        eContext = context;
        image_00 = imageView;
        appGraphics = new AppGraphics(context);
        progressBar = imageProgress;

        // Load Messages
        Resources res = context.getResources();
        loadingMessages = res.getStringArray(R.array.loading_messages);
    }

    @Override
    protected void onPreExecute() {
        /*progressDialog = ProgressDialog.show(eContext,
                eContext.getResources().getString(R.string.progress_search_00),
                eContext.getResources().getString(R.string.progress_search_01));*/
    }

    @Override
    protected String doInBackground(String... params) {

        // Download needed contents
        imageDownload = appGraphics.getBitmapFromURL(params[0]);
        imageDownload = appGraphics.getResizedBitmap(imageDownload, 780, 300);

        return errorMessage;
    }

    @Override
    protected void onPostExecute(String args) {

        // Uploads image onto article
        progressBar.setVisibility(View.INVISIBLE);
        image_00.setImageBitmap(imageDownload);

        // Dismiss Progress Dialog
        //progressDialog.dismiss();
    }

    // Randomize a Message
    public String randomMessage(){
        Random rand = new Random();
        int randNum = rand.nextInt(loadingMessages.length);
        String messageR = loadingMessages[randNum];
        return messageR;
    }
}
