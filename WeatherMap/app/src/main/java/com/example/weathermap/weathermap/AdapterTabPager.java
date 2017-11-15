package com.example.weathermap.weathermap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * The adapter for the tabs on the pager.
 */

public class AdapterTabPager extends FragmentStatePagerAdapter {

    // Variables of the adapter
    int tabAmount;

    public AdapterTabPager(FragmentManager fm, int tabNumber) {
        super(fm);
        this.tabAmount = tabNumber;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // Returns the fragment of today's weather
                FragmentTodayWeather fragToday = new FragmentTodayWeather();
                return fragToday;
            case 1:
                // Returns the fragment of the weather of five days
                FragmentFiveWeather fragFiveDays = new FragmentFiveWeather();
                return fragFiveDays;
            case 2:
                // Returns the fragment of options
                FragmentOptions fragOptions = new FragmentOptions();
                return fragOptions;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabAmount;
    }

}
