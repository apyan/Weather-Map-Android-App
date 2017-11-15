package com.example.weathermap.weathermap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.weathermap.weathermap.AppFunctions.AppJSONStorage;

/**
 * The fragment that displays options for the user.
 */

public class FragmentOptions extends Fragment {

    // Variables used for the fragment
    public AppJSONStorage appJSONStorage;

    public Switch switch_00;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set up the fragment
        View v = inflater.inflate(R.layout.fragment_options, container, false);

        // Set up needed classes
        appJSONStorage = new AppJSONStorage(getActivity(), getString(R.string.user_file));
        appJSONStorage.dataRead();

        // Set up UI elements
        switch_00 = (Switch) v.findViewById(R.id.optionSwitch_00);

        // Set up switch_00: Title Screen preference
        if(appJSONStorage.titleScreenOn) {
            switch_00.setChecked(true);
        } else {
            switch_00.setChecked(false);
        }

        // Upon activity switch_00: Title Screen preference
        switch_00.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean boolChecked) {
                // Saves user's preference
                appJSONStorage.titleScreenOn = boolChecked;
                appJSONStorage.dataWrite();
            }
        });

        return v;
    }

}
