package com.example.weathermap.weathermap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weathermap.weathermap.AppFunctions.AppJSONStorage;

/**
 * The fragment that displays options for the user.
 */

public class FragmentOptions extends Fragment {

    // Variables used for the fragment
    public AppJSONStorage appJSONStorage;

    public Switch switch_00;
    public TextView text_00;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set up the fragment
        View v = inflater.inflate(R.layout.fragment_options, container, false);

        // Set up needed classes
        appJSONStorage = new AppJSONStorage(getActivity(), getString(R.string.user_file));
        appJSONStorage.dataRead();

        // Set up UI elements
        switch_00 = (Switch) v.findViewById(R.id.optionSwitch_00);
        text_00 = (TextView) v.findViewById(R.id.description_00);

        // Set up switch_00: Title Screen preference
        // and change the description text
        if(appJSONStorage.titleScreenOn) {
            switch_00.setChecked(true);
            text_00.setText(R.string.options_000_on);
        } else {
            switch_00.setChecked(false);
            text_00.setText(R.string.options_000_off);
        }

        // Upon activity switch_00: Title Screen preference
        switch_00.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean boolChecked) {
                // Saves user's preference
                appJSONStorage.titleScreenOn = boolChecked;
                appJSONStorage.dataWrite();

                // Change the description text
                if(boolChecked) {
                    text_00.setText(R.string.options_000_on);
                } else {
                    text_00.setText(R.string.options_000_off);
                }
            }
        });

        return v;
    }

}
