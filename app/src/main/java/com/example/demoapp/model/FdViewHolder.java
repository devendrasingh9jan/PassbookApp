package com.example.demoapp.model;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.R;

public class FdViewHolder extends RecyclerView.ViewHolder {

    public TextView amountValueView, tenureValueView, daysLeftView;
    public FdViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeFields(itemView);
    }

    private void initializeFields(View itemView) {
        amountValueView = itemView.findViewById(R.id.textViewAmountValue);
        tenureValueView = itemView.findViewById(R.id.textViewTenureValue);
        daysLeftView = itemView.findViewById(R.id.textViewDaysLeftValue);
    }
}
