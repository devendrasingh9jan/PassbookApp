package com.example.demoapp.model;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.R;

public class FdViewHolder extends RecyclerView.ViewHolder {

    public TextView amountValueView, tenureValueView, daysLeftView, startDateView, endDateView,
            notesValueView, maturityAmountView, bankAddressView, rateView, numberView;
    public ProgressBar progressBarDaysLeft;
    public CardView fdCardView;
    public RelativeLayout expandableLayout;


    public FdViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeFields(itemView);
    }

    private void initializeFields(View itemView) {
        amountValueView = itemView.findViewById(R.id.textViewAmountValue);
        tenureValueView = itemView.findViewById(R.id.textViewTenureValue);
        daysLeftView = itemView.findViewById(R.id.textViewDaysLeftValue);
        progressBarDaysLeft = itemView.findViewById(R.id.progressBarDaysLeft);
        fdCardView = itemView.findViewById(R.id.fdCardView);
        expandableLayout = itemView.findViewById(R.id.expandableLayout);
        notesValueView = itemView.findViewById(R.id.textViewNotesValue);
        startDateView = itemView.findViewById(R.id.textViewStartDateValue);
        endDateView = itemView.findViewById(R.id.textViewEndDateValue);
        maturityAmountView = itemView.findViewById(R.id.textViewMaturityAmountValue);
        bankAddressView = itemView.findViewById(R.id.textViewBankAddressValue);
        rateView = itemView.findViewById(R.id.textViewRateValue);
        numberView = itemView.findViewById(R.id.textViewNumber);
    }
}
