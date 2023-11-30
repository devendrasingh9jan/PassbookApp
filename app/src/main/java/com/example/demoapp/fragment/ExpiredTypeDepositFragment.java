package com.example.demoapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demoapp.R;
import com.example.demoapp.activity.MainActivity;
import com.example.demoapp.adapter.RecyclerFixedDepositAdapter;
import com.example.demoapp.model.FixedDeposit;
import com.example.demoapp.model.User;
import com.example.demoapp.repository.FdRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExpiredTypeDepositFragment extends Fragment {

    private RecyclerFixedDepositAdapter expiredAdapter;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expired, container, false);
        initializeFields(view);
        MainActivity mainActivity = (MainActivity) getActivity();

        // Initialize the adapter
        expiredAdapter = new RecyclerFixedDepositAdapter(mainActivity, getExpiredDepositList());

        // Set the layout manager and adapter for the active RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(expiredAdapter);

        return view;
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
            return fixedDepositList.stream().filter(fd -> fd.getDaysLeft() <= 0).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    private void initializeFields(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewExpiredFixedDeposits);
    }
}