package com.example.demoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.R;
import com.example.demoapp.model.FdViewHolder;
import com.example.demoapp.model.FixedDeposit;

import java.util.List;

public class RecyclerFixedDepositAdapter extends RecyclerView.Adapter<FdViewHolder> {

    private Context context;
    private List<FixedDeposit> fixedDepositList;

    public RecyclerFixedDepositAdapter(Context context, List<FixedDeposit> fixedDepositList) {
        this.context = context;
        this.fixedDepositList = fixedDepositList;
    }

    @NonNull
    @Override
    public FdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_fixed_deposit, parent, false);
        return new FdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FdViewHolder holder, int position) {
        FixedDeposit fixedDeposit = fixedDepositList.get(position);
        holder.amountValueView.setText(String.valueOf(fixedDeposit.getAmount()));
        holder.tenureValueView.setText(String.valueOf(fixedDeposit.getTenure()));
    }

    @Override
    public int getItemCount() {
        return fixedDepositList.size();
    }
}
