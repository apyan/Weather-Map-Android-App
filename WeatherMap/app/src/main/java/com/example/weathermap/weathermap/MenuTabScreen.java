package com.example.weathermap.weathermap;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class MenuTabScreen extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    // Variables of the activity
    public TabLayout tabLayout;
    public ViewPager viewPager;
    public AdapterTabPager tabPagerAdapter;
    public InputMethodManager inputMethodManager;

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

        // For hiding keyboard to prevent UI confusion
        inputMethodManager = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

        // Upon changing tabs
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
