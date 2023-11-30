package com.example.demoapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.demoapp.fragment.ActiveTypeDepositFragment;
import com.example.demoapp.fragment.ExpiredTypeDepositFragment;

public class FixedDepositViewPagerAdapter extends FragmentStateAdapter {
    public FixedDepositViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new ExpiredTypeDepositFragment();
            default:
                return new ActiveTypeDepositFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
