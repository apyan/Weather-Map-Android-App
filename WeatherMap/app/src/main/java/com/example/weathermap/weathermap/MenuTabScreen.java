package com.example.weathermap.weathermap;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MenuTabScreen extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    // Variables of the activity
    public TabLayout tabLayout;
    public ViewPager viewPager;
    public AdapterTabPager tabPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tab_screen);

        // Set up the Tab Layout UI elements
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getText(R.string.tab_000)));
        tabLayout.addTab(tabLayout.newTab().setText(getText(R.string.tab_001)));
        tabLayout.addTab(tabLayout.newTab().setText(getText(R.string.tab_002)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Set up the View Pager UI elements
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabPagerAdapter = new AdapterTabPager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
