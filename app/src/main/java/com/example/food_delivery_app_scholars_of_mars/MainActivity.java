package com.example.food_delivery_app_scholars_of_mars;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.food_delivery_app_scholars_of_mars.fragment.AccountLoginFragment;
import com.example.food_delivery_app_scholars_of_mars.fragment.FavoriteFragment;
import com.example.food_delivery_app_scholars_of_mars.fragment.HistoryFragment;
import com.example.food_delivery_app_scholars_of_mars.fragment.HomeFragment;
import com.example.food_delivery_app_scholars_of_mars.fragment.ProfileFragment;
import com.example.food_delivery_app_scholars_of_mars.session.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    HistoryFragment historyFragment = new HistoryFragment();
    FavoriteFragment favoriteFragment = new FavoriteFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);


        Window window = this.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(this.getResources().getColor(R.color.background));
        window.setNavigationBarColor(this.getResources().getColor(R.color.background));

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        drawerLayout =findViewById(R.id.drawerLayout);

        findViewById(R.id.left_menu_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView =findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, homeFragment).commit();
                        break;
                    case R.id.favorite:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, favoriteFragment).commit();
                        break;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, profileFragment).commit();
                        break;
                    case R.id.history:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, historyFragment).commit();
                        break;
                }
                return false;
            }
        });
    }

    public void actionMenuHundeler(View view) {
        if (view.getId() == R.id.shopping_craft){
            Intent intent = new Intent(MainActivity.this, ShoppingCraftActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.order){
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.sign_out){
            SessionManager sessionManager = new SessionManager(MainActivity.this);
            sessionManager.logOut();
            startActivity(new Intent(MainActivity.this, AcountActivity.class));
            Toast.makeText(this, "Sign out success", Toast.LENGTH_SHORT).show();
            finish();
        }
        return false;
    }
}