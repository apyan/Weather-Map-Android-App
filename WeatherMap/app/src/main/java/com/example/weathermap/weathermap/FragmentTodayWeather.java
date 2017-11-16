package com.example.weathermap.weathermap;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weathermap.weathermap.AppFunctions.AppConnect;
import com.example.weathermap.weathermap.AppFunctions.AppJSONStorage;
import com.example.weathermap.weathermap.AppFunctions.AsyncTodaySearchTask;
import com.example.weathermap.weathermap.AppObjects.CityTodayObject;

/**
 * The fragment that displays today's weather data.
 */

public class FragmentTodayWeather extends Fragment implements View.OnClickListener {

    // Variables used for the fragment
    public AppConnect appConnect;
    public AppJSONStorage appJSONStorage;
    public String searchQuery;
    public CityTodayObject cityTodayObject;
    public AsyncTodaySearchTask asyncTodaySearchTask;
    public InputMethodManager inputMethodManager;

    public Button button_00;
    public EditText edit_00;
    public TextView text_00, text_01, text_02, text_03, text_04, text_05, text_06, text_07,
            text_08, text_09, text_10, text_11, text_12;
    public LinearLayout linear_00, linear_01, linear_02, linear_03, linear_04, linear_05;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set up the fragment
        View v = inflater.inflate(R.layout.fragment_today_weather, container, false);

        // Set up needed classes
        appConnect = new AppConnect(getActivity());
        appJSONStorage = new AppJSONStorage(getActivity(), getString(R.string.user_file));
        appJSONStorage.dataRead();

        // Set up UI elements
        button_00 = (Button) v.findViewById(R.id.search_button);
        edit_00 = (EditText) v.findViewById(R.id.search_edit);
        linear_00 = (LinearLayout) v.findViewById(R.id.data_linear);
        linear_01 = (LinearLayout) v.findViewById(R.id.welcome_linear);
        linear_02 = (LinearLayout) v.findViewById(R.id.loading_linear);
        linear_03 = (LinearLayout) v.findViewById(R.id.connectError_linear);
        linear_04 = (LinearLayout) v.findViewById(R.id.searchError_linear);
        text_00 = (TextView) v.findViewById(R.id.loading_text);

        // Data UI elements
        text_01 = (TextView) v.findViewById(R.id.data_city);
        text_02 = (TextView) v.findViewById(R.id.data_country);
        text_03 = (TextView) v.findViewById(R.id.data_coordinates);
        text_04 = (TextView) v.findViewById(R.id.data_temperature);
        text_05 = (TextView) v.findViewById(R.id.data_tempMin);
        text_06 = (TextView) v.findViewById(R.id.data_tempMax);
        text_07 = (TextView) v.findViewById(R.id.data_pressure);
        text_08 = (TextView) v.findViewById(R.id.data_humidity);
        text_09 = (TextView) v.findViewById(R.id.data_wind);
        text_10 = (TextView) v.findViewById(R.id.data_cloud);
        text_11 = (TextView) v.findViewById(R.id.data_sunrise);
        text_12 = (TextView) v.findViewById(R.id.data_sunset);
        linear_05 = (LinearLayout) v.findViewById(R.id.forecast_data);

        // Button onClick Inputs
        button_00.setOnClickListener(this);

        // Checks for first time user status
        if(appJSONStorage.lastCitySearched.equals("")) {
            // Affects which field is displayed
            linear_00.setVisibility(View.GONE);
            linear_01.setVisibility(View.VISIBLE);
            linear_02.setVisibility(View.GONE);
            linear_03.setVisibility(View.GONE);
            linear_04.setVisibility(View.GONE);
        } else {

            // Pre-Fill the search query
            linear_01.setVisibility(View.GONE);
            edit_00.setText(appJSONStorage.lastCitySearched);

            // Checks for connection
            if(!appConnect.connectionAvailable()) {
                // Affects which field is displayed
                linear_00.setVisibility(View.GONE);
                linear_02.setVisibility(View.GONE);
                linear_03.setVisibility(View.VISIBLE);
                linear_04.setVisibility(View.GONE);
            } else {
                linear_03.setVisibility(View.GONE);

                // AsyncTask process on retrieving information
                asyncTodaySearchTask = new AsyncTodaySearchTask(getContext(),
                        text_00, linear_00, linear_02, linear_04, text_01, text_02, text_03, text_04,
                        text_05, text_06, text_07, text_08, text_09, text_10, text_11, text_12,
                        linear_05);
                asyncTodaySearchTask.execute(appJSONStorage.lastCitySearched);
                appJSONStorage.dataRead();
            }
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Search Button
            case R.id.search_button:
                // For hiding keyboard to prevent UI confusion
                inputMethodManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);

                // Checks for empty query
                searchQuery = edit_00.getText().toString().trim();
                linear_01.setVisibility(View.GONE);
                if(searchQuery.equals("")) {
                    // Affects which field is displayed
                    linear_00.setVisibility(View.GONE);
                    linear_02.setVisibility(View.GONE);
                    linear_03.setVisibility(View.GONE);
                    linear_04.setVisibility(View.VISIBLE);
                } else {

                    // Checks for connection
                    if(!appConnect.connectionAvailable()) {
                        // Affects which field is displayed
                        linear_00.setVisibility(View.GONE);
                        linear_02.setVisibility(View.GONE);
                        linear_03.setVisibility(View.VISIBLE);
                        linear_04.setVisibility(View.GONE);
                    } else {
                        linear_03.setVisibility(View.GONE);

                        // AsyncTask process on retrieving information
                        asyncTodaySearchTask = new AsyncTodaySearchTask(getContext(),
                                text_00, linear_00, linear_02, linear_04, text_01, text_02, text_03,
                                text_04, text_05, text_06, text_07, text_08, text_09, text_10,
                                text_11, text_12, linear_05);
                        asyncTodaySearchTask.execute(searchQuery);
                        appJSONStorage.dataRead();
                    }
                }
                break;
            default:
                break;
        }
    }
}
