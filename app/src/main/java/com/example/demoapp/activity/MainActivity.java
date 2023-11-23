package com.example.demoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.demoapp.R;
import com.example.demoapp.adapter.FixedDepositViewPagerAdapter;
import com.example.demoapp.model.User;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private PopupWindow popupWindow;
    private ImageView createFdIcon, depositsImageView, expensesImageView;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private FixedDepositViewPagerAdapter fixedDepositViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeFields();
        handleFdTypeFragments();
        createFdIcon.setOnClickListener(view ->{
            showOptionsPopup(view);
        });
        footerNavigation();
    }
    private void handleFdTypeFragments() {
        fixedDepositViewPagerAdapter = new FixedDepositViewPagerAdapter(this);
        viewPager2.setAdapter(fixedDepositViewPagerAdapter);
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

    private void footerNavigation() {
        final boolean[] isGrayImage = {true}; // Initial state is gray
        depositsImageView.setOnClickListener(view -> {
            if (isGrayImage[0]) {
                depositsImageView.setImageResource(R.drawable.ic_deposit_coloured);
                isGrayImage[0] = false;
            } else {
                depositsImageView.setImageResource(R.drawable.ic_deposit_gray);
                isGrayImage[0] = true;
            }
            Intent intent1 = new Intent(getIntent());
            intent1.setClass(this, MainActivity.class);
            startActivity(intent1);
        });

        expensesImageView.setOnClickListener(view -> {
            if (isGrayImage[0]) {
                expensesImageView.setImageResource(R.drawable.ic_expenses_rupee_coloured);
                isGrayImage[0] = false;
            } else {
                expensesImageView.setImageResource(R.drawable.ic_expenses_rupee_gray);
                isGrayImage[0] = true;
            }
            Intent intent1 = new Intent(getIntent());
            intent1.setClass(this, MainActivityExpenses.class);
            startActivity(intent1);
        });
    }
    private void showOptionsPopup(View anchorView) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.options_popup, null);

        ImageView cameraOption = popupView.findViewById(R.id.depositCameraView);
        ImageView formOption = popupView.findViewById(R.id.depositFormView);

        // Create and configure the PopupWindow
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAsDropDown(anchorView);

        // Handle option 1 button click
        cameraOption.setOnClickListener(view -> {
            cameraLayout();
        });

        // Handle option 2 button click
        formOption.setOnClickListener(view -> {
            formLayout();
        });
    }

    private void cameraLayout() {
        Toast.makeText(this, "Opening camera", Toast.LENGTH_SHORT).show();

    }

    private void formLayout() {
        User loggedInUser = getLoggedInUser();
        Intent intent2 = new Intent(this, CreateFdLayout.class);
        intent2.putExtra("loggedInUser", loggedInUser);
        startActivity(intent2);
    }

    public User getLoggedInUser() {
        Intent intent = getIntent();
        if (intent.hasExtra("loggedInUser")) {
            User loggedInUser = (User) intent.getSerializableExtra("loggedInUser");
            return loggedInUser;
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        return null;
    }

    private void initializeFields() {
        createFdIcon = findViewById(R.id.createFdIcon);
        depositsImageView = findViewById(R.id.depositsImageView);
        expensesImageView = findViewById(R.id.expensesImageView);
        tabLayout = findViewById(R.id.fd_type_tab_layout);
        viewPager2 = findViewById(R.id.view_fd_pager);

    }
}