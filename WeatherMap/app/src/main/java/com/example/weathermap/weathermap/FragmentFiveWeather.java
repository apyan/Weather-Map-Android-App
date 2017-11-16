package com.example.weathermap.weathermap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.weathermap.weathermap.AppFunctions.AppConnect;
import com.example.weathermap.weathermap.AppFunctions.AppJSONStorage;

/**
 * The fragment that displays the weather forecast of the city for the next five days.
 */

public class FragmentFiveWeather extends Fragment implements View.OnClickListener {

    // Variables used for the fragment
    public AppConnect appConnect;
    public AppJSONStorage appJSONStorage;
    public String searchQuery;

    public Button button_00;
    public EditText edit_00;
    public LinearLayout linear_00, linear_01, linear_02, linear_03, linear_04;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set up the fragment
        View v = inflater.inflate(R.layout.fragment_five_weather, container, false);

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

        // Button onClick Inputs
        button_00.setOnClickListener(this);

        // Checks for the last city viewed
        if(appJSONStorage.lastCityID == 0) {

        }

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
