package com.example.demoapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Helper.SwipeHelper;
import com.example.demoapp.R;
import com.example.demoapp.adapter.RecyclerFixedDepositAdapter;
import com.example.demoapp.model.FixedDeposit;
import com.example.demoapp.model.User;
import com.example.demoapp.repository.FdRepository;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private PopupWindow popupWindow;
    private RecyclerView recyclerViewActive;
    private RecyclerView recyclerViewExpired;
    private ImageView createFdIcon;
    private ImageView depositsImageView, expensesImageView;
    private TextView activeFd, expiredFd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeFields();
        setActiveTab(activeFd);
        showActiveFixedDeposits();
        activeFd.setOnClickListener(view -> {
            setActiveTab(activeFd);
            showActiveFixedDeposits();
        });

        expiredFd.setOnClickListener(view -> {
            setActiveTab(expiredFd);
            showExpiredFixedDeposits();
        });
        createFdIcon.setOnClickListener(view ->{
            showOptionsPopup(view);
        });

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

    private void setActiveTab(TextView selectedTab) {
        // Reset background for both tabs
        activeFd.setBackgroundResource(R.drawable.textview_highlight_selector);
        expiredFd.setBackgroundResource(R.drawable.textview_highlight_selector);

        // Reset text color for both tabs
        activeFd.setTextColor(ContextCompat.getColor(this, R.color.black)); // Change to your default text color
        expiredFd.setTextColor(ContextCompat.getColor(this, R.color.black)); // Change to your default text color

        activeFd.setSelected(false);
        expiredFd.setSelected(false);

        // Clear the RecyclerView and adapter for the non-selected tab
        if (selectedTab == activeFd) {
            recyclerViewExpired.setAdapter(null);
        } else {
            recyclerViewActive.setAdapter(null);
        }

        // Set the selected tab background
        selectedTab.setBackgroundResource(R.color.purple); // Change to your selected tab background
        selectedTab.setTextColor(ContextCompat.getColor(this, R.color.white)); // Change to your selected tab text color
        selectedTab.setSelected(true);
    }


    private void showActiveFixedDeposits() {
        FdRepository fdRepository = new FdRepository(this);
        User loggedInUser = getLoggedInUser();
        List<FixedDeposit> fixedDepositList = fdRepository.getAllFixedDeposits(loggedInUser.getId());
        List<FixedDeposit> activeFixedDeposits = fixedDepositList.stream().filter(fd -> fd.getTenure()>0).collect(Collectors.toList());

        if (activeFd.isSelected()) {
            RecyclerFixedDepositAdapter adapter = new RecyclerFixedDepositAdapter(this, activeFixedDeposits);
            recyclerViewActive.setAdapter(adapter);
            recyclerViewActive.setLayoutManager(new LinearLayoutManager(this));
            new SwipeHelper(this, recyclerViewActive, false) {
                @Override
                public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                    // Break Button
                    underlayButtons.add(new UnderlayButton(
                            "Break",
                            AppCompatResources.getDrawable(MainActivity.this, R.drawable.ic_cancel_coloured),
                            Color.parseColor("#FF0000"),
                            Color.parseColor("#ffffff"),
                            pos -> {
                                activeFixedDeposits.remove(pos);
                                Toast.makeText(MainActivity.this, "Fixed Deposit Closed ", Toast.LENGTH_SHORT).show();
                                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                            }
                    ));

                    // Renew Button
                    underlayButtons.add(new UnderlayButton(
                            "Renew",
                            AppCompatResources.getDrawable(MainActivity.this, R.drawable.ic_renew_coloured),
                            Color.parseColor("#00FF00"),
                            Color.parseColor("#ffffff"),
                            pos -> {
                                Toast.makeText(MainActivity.this, "More Button Clicked at Position: " + pos, Toast.LENGTH_SHORT).show();
                                adapter.notifyItemChanged(pos);
                            }
                    ));
                }
            };


        }

    }

    private void showExpiredFixedDeposits() {
        FdRepository fdRepository = new FdRepository(this);
        User loggedInUser = getLoggedInUser();
        List<FixedDeposit> fixedDepositList = fdRepository.getAllFixedDeposits(loggedInUser.getId());

        List<FixedDeposit> expiredFixedDeposits = fixedDepositList.stream().filter(fd -> fd.getTenure()<0).collect(Collectors.toList());

        if (expiredFd.isSelected()) {
            RecyclerFixedDepositAdapter adapter = new RecyclerFixedDepositAdapter(this, expiredFixedDeposits);
            recyclerViewExpired.setAdapter(adapter);
            recyclerViewExpired.setLayoutManager(new LinearLayoutManager(this));
            new SwipeHelper(this, recyclerViewExpired, false) {
                @Override
                public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                    // Break Button
                    underlayButtons.add(new UnderlayButton(
                            "Remove",
                            AppCompatResources.getDrawable(MainActivity.this, R.drawable.ic_cancel_coloured),
                            Color.parseColor("#FF0000"),
                            Color.parseColor("#ffffff"),
                            pos -> {
                                expiredFixedDeposits.remove(pos);
                                Toast.makeText(MainActivity.this, "Fixed Deposit Removed ", Toast.LENGTH_SHORT).show();
                                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                            }
                    ));
                }
            };
        }

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
        recyclerViewActive = findViewById(R.id.recyclerViewActiveFixedDeposits);
        recyclerViewExpired = findViewById(R.id.recyclerViewExpiredFixedDeposits);
        activeFd = findViewById(R.id.activeFd);
        expiredFd = findViewById(R.id.expiredFd);
        depositsImageView = findViewById(R.id.depositsImageView);
        expensesImageView = findViewById(R.id.expensesImageView);
    }
}