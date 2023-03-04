package com.example.food_delivery_app_scholars_of_mars;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.food_delivery_app_scholars_of_mars.fragment.AccountLoginFragment;
import com.example.food_delivery_app_scholars_of_mars.fragment.CreateAccountFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new CreateAccountFragment();
            default:
                return new AccountLoginFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
