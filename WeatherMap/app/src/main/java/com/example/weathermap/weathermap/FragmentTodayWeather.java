package com.example.weathermap.weathermap;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.weathermap.weathermap.AppFunctions.AppConnect;
import com.example.weathermap.weathermap.AppFunctions.AppJSONStorage;

/**
 * The fragment that displays today's weather data.
 */

public class FragmentTodayWeather extends Fragment implements View.OnClickListener {

    // Variables used for the fragment
    public AppConnect appConnect;
    public AppJSONStorage appJSONStorage;
    public String searchQuery;

    public Button button_00;
    public EditText edit_00;

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

        // Button onClick Inputs
        button_00.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Search Button
            case R.id.search_button:
                searchQuery = edit_00.getText().toString().trim();
                break;
            default:
                break;
        }
    }
}
