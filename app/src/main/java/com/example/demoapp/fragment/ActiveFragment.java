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

public class ActiveFragment extends Fragment {
    private RecyclerFixedDepositAdapter activeAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active, container, false);
        initializeFields(view);
        MainActivity mainActivity = (MainActivity) getActivity();

        // Initialize the adapter
        activeAdapter = new RecyclerFixedDepositAdapter(mainActivity, getActiveDepositList());

        // Set the layout manager and adapter for the active RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(activeAdapter);

        return view;
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
            return fixedDepositList.stream().filter(fd -> fd.getTenure() > 0).collect(Collectors.toList());
        }

        return new ArrayList<>(); // Return an empty list if activity is null
    }

    private void initializeFields(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewActiveFixedDeposits);
    }
}
