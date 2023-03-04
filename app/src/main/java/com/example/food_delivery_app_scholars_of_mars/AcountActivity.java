package com.example.food_delivery_app_scholars_of_mars;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.google.android.material.tabs.TabLayout;

public class AcountActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount);
        Window window = this.getWindow();

        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        window.setNavigationBarColor(this.getResources().getColor(R.color.background));




        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2 = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_items);
        viewPager2.setAdapter(viewPagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }
}