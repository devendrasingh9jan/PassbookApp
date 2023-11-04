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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.R;
import com.example.demoapp.adapter.RecyclerFixedDepositAdapter;
import com.example.demoapp.model.FixedDeposit;
import com.example.demoapp.model.User;
import com.example.demoapp.repository.FdRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PopupWindow popupWindow;
    private RecyclerView recyclerView;
    private ImageView createFdIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeFields();
        showFixedDeposits();
        createFdIcon.setOnClickListener(view ->{
            showOptionsPopup(view);
        });

    }

    private void showFixedDeposits() {
        FdRepository fdRepository = new FdRepository(this);
        User loggedInUser = getLoggedInUser();
        List<FixedDeposit> fixedDepositList = fdRepository.getAllFixedDeposits(loggedInUser.getId());
        recyclerView.setAdapter(new RecyclerFixedDepositAdapter(this, fixedDepositList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        User loggedInUser = getLoggedInUser();
        Intent intent2 = new Intent(this, CreateFdLayout.class);
            intent2.putExtra("loggedInUser", loggedInUser);
            startActivity(intent2);
    }

    private User getLoggedInUser(){
        Intent intent = getIntent();
        if (intent.hasExtra("loggedInUser")) {
            User loggedInUser = (User) intent.getSerializableExtra("loggedInUser");
            return loggedInUser;
        } else {
            startActivity(new Intent(this,LoginActivity.class));
        }
        return null;
    }

    private void initializeFields() {
        createFdIcon = findViewById(R.id.createFdIcon);
        recyclerView = findViewById(R.id.recyclerViewFixedDeposits);
    }
}