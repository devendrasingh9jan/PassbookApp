package com.example.demoapp.fragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.R;
import com.example.demoapp.activity.CreateFdLayout;
import com.example.demoapp.activity.MainActivity;
import com.example.demoapp.adapter.FixedDepositViewPagerAdapter;
import com.example.demoapp.model.FixedDeposit;
import com.example.demoapp.model.User;
import com.example.demoapp.repository.FdRepository;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DepositsFragment extends Fragment {

    private PopupWindow popupWindow;
    private ImageView createFdIcon;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private FixedDepositViewPagerAdapter fixedDepositViewPagerAdapter;
    private TextView totalInvestedValue,totalCompletionValue;

    MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deposits, container, false);
        initializeFields(view);
        mainActivity = (MainActivity) getActivity();
        createFdIcon.setOnClickListener(item ->{
            showOptionsPopup(item);
        });
        handleFdTypeFragments();
        return view;
    }

    private void showOptionsPopup(View anchorView) {
        LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
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
        Toast.makeText(mainActivity, "Opening camera", Toast.LENGTH_SHORT).show();

    }

    private void formLayout() {
        User loggedInUser = mainActivity.getLoggedInUser();
        Intent intent2 = new Intent(mainActivity, CreateFdLayout.class);
        intent2.putExtra("loggedInUser", loggedInUser);
        startActivity(intent2);
    }

    private void handleFdTypeFragments() {
        fixedDepositViewPagerAdapter = new FixedDepositViewPagerAdapter(mainActivity);
        viewPager2.setAdapter(fixedDepositViewPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                double activeInvested = getActiveDepositList().stream().mapToDouble(FixedDeposit::getAmount).sum();
                double activeCompletion = getActiveDepositList().stream().mapToDouble(FixedDeposit::getMaturityAmount).sum();
                double expiredInvested = getExpiredDepositList().stream().mapToDouble(FixedDeposit::getAmount).sum();
                double expiredCompletion = getExpiredDepositList().stream().mapToDouble(FixedDeposit::getMaturityAmount).sum();
                if(tab.getPosition()==0) {
                    totalInvestedValue.setText("\u20B9" + activeInvested);
                    totalCompletionValue.setText("\u20B9" + activeCompletion);
                }
                if(tab.getPosition()==1) {
                    totalInvestedValue.setText("\u20B9" + expiredInvested);
                    totalCompletionValue.setText("\u20B9" + expiredCompletion);
                }
                viewPager2.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                double activeInvested = getActiveDepositList().stream().mapToDouble(FixedDeposit::getAmount).sum();
                double activeCompletion = getActiveDepositList().stream().mapToDouble(FixedDeposit::getMaturityAmount).sum();
                double expiredInvested = getExpiredDepositList().stream().mapToDouble(FixedDeposit::getAmount).sum();
                double expiredCompletion = getExpiredDepositList().stream().mapToDouble(FixedDeposit::getMaturityAmount).sum();
                if(position==0) {
                    totalInvestedValue.setText("\u20B9" + activeInvested);
                    totalCompletionValue.setText("\u20B9" + activeCompletion);
                }
                if(position==1) {
                    totalInvestedValue.setText("\u20B9" + expiredInvested);
                    totalCompletionValue.setText("\u20B9" + expiredCompletion);
                }
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }

    private List<FixedDeposit> getActiveDepositList() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            // Access the repository and retrieve the data
            FdRepository fdRepository = new FdRepository(mainActivity);
            User loggedInUser = mainActivity.getLoggedInUser();
            List<FixedDeposit> fixedDepositList = fdRepository.getAllFixedDeposits(loggedInUser.getId());

            // Log the size of the fixedDepositList for debugging
            Log.d("ActiveFragment", "Number of active deposits: " + fixedDepositList.size());

            // Filter active deposits
            return fixedDepositList.stream().filter(fd -> fd.getDaysLeft() >= 0).collect(Collectors.toList());
        }

        return new ArrayList<>(); // Return an empty list if activity is null
    }

    private List<FixedDeposit> getExpiredDepositList() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            // Access the repository and retrieve the data
            FdRepository fdRepository = new FdRepository(mainActivity);
            User loggedInUser = mainActivity.getLoggedInUser();
            List<FixedDeposit> fixedDepositList = fdRepository.getAllFixedDeposits(loggedInUser.getId());

            // Log the size of the fixedDepositList for debugging
            Log.d("ExpiredFragment", "Number of expired deposits: " + fixedDepositList.size());

            // Filter active deposits
            return fixedDepositList.stream().filter(fd -> fd.getDaysLeft() < 0).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    private void initializeFields(View view) {
        createFdIcon = view.findViewById(R.id.createFdIcon);
        tabLayout = view.findViewById(R.id.fd_type_tab_layout);
        viewPager2 = view.findViewById(R.id.view_fd_pager);
        totalInvestedValue = view.findViewById(R.id.totalInvestedValue);
        totalCompletionValue = view.findViewById(R.id.totalCompletionValue);
    }
}