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
import com.example.demoapp.fragment.DepositsFragment;
import com.example.demoapp.fragment.ExpensesFragment;
import com.example.demoapp.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    DepositsFragment depositsFragment = new DepositsFragment();
    ExpensesFragment expensesFragment = new ExpensesFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeFields();
        footerNavigation();
    }

    private void footerNavigation() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, depositsFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.deposits) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, depositsFragment).commit();
                return true;
            } else if (itemId == R.id.expenses) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, expensesFragment).commit();
                return true;
            }
            return false;
        });
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
        bottomNavigationView = findViewById(R.id.bottomNavigation);
    }
}