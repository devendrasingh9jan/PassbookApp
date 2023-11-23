package com.example.demoapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.demoapp.fragment.ActiveFragment;
import com.example.demoapp.fragment.ExpiredFragment;

public class FixedDepositViewPagerAdapter extends FragmentStateAdapter {
    public FixedDepositViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new ExpiredFragment();
            default:
                return new ActiveFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
