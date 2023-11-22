package com.example.demoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demoapp.R;

public class MainActivityExpenses extends AppCompatActivity {
    private ImageView depositsImageView, expensesImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_expenses);
        initializeFields();
        footerNavigation();


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
            intent1.setClass(this,MainActivity.class);
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
            intent1.setClass(this,MainActivityExpenses.class);
            startActivity(intent1);
        });
    }

    private void initializeFields() {
        depositsImageView = findViewById(R.id.depositsImageView);
        expensesImageView = findViewById(R.id.expensesImageView);
    }
}