package com.example.demoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapp.R;
import com.example.demoapp.model.User;

public class MainActivity extends AppCompatActivity {

    private PopupWindow popupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView createFdIcon = findViewById(R.id.createFdIcon);

        // Handle "create" icon click
        createFdIcon.setOnClickListener(view ->{
            showOptionsPopup(view);
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
        cameraOption.setOnClickListener(view ->{
            cameraLayout();
        });

        // Handle option 2 button click
        formOption.setOnClickListener(view ->{
            formLayout();
        });
    }

    private void cameraLayout() {
        Toast.makeText(this,"Opening camera",Toast.LENGTH_SHORT).show();

    }

    private void formLayout() {
        Intent intent = getIntent();
        if (intent.hasExtra("loggedInUser")) {
            User loggedInUser = (User) intent.getSerializableExtra("loggedInUser");

            // Create a new Intent to start CreateFdLayout
            Intent intent2 = new Intent(this, CreateFdLayout.class);
            intent2.putExtra("loggedInUser", loggedInUser);

            // Start CreateFdLayout with the new Intent
            startActivity(intent2);
        }
    }
}