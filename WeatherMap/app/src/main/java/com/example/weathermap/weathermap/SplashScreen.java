package com.example.weathermap.weathermap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weathermap.weathermap.AppFunctions.AppGraphics;
import com.example.weathermap.weathermap.AppFunctions.AppJSONStorage;

public class SplashScreen extends AppCompatActivity {

    // Variables of the activity
    public AppGraphics appGraphics;
    public AppJSONStorage appJSONStorage;
    public Animation anim = new AlphaAnimation(0.0f, 1.0f);

    public TextView text_00;
    public LinearLayout linear_00, linear_01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Initiates the classes needed
        appGraphics = new AppGraphics(this);
        appJSONStorage = new AppJSONStorage(this, getString(R.string.user_file));

        // Coordinate with the UI elements
        text_00 = (TextView) findViewById(R.id.prompt_text);
        linear_00 = (LinearLayout) findViewById(R.id.linear_000);
        linear_01 = (LinearLayout) findViewById(R.id.linear_001);

        // Give the prompting text a blinking effect
        anim.setDuration(500);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        text_00.startAnimation(anim);

        // Creates the user data file, if it doesn't exist
        if(!appJSONStorage.fileExists()) {
            appJSONStorage.initializeFile();
        }
        appJSONStorage.dataRead();

        // Checks for the option of the Title Screen appearance
        if(!appJSONStorage.titleScreenOn) {
            // Heads to the Menu Tab Screen activity
            startActivity(new Intent(getApplicationContext(), MenuTabScreen.class));
            finish();
        }
    }

    // Proceeds to the Tab Screen activity
    public void onTabScreenProceed(View v){
        // Heads to the Menu Tab Screen activity
        startActivity(new Intent(getApplicationContext(), MenuTabScreen.class));
        finish();
    }
}
