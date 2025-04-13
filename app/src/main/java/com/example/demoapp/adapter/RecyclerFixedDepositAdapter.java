package com.example.demoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.R;
import com.example.demoapp.activity.CreateFdLayout;
import com.example.demoapp.activity.MainActivity;
import com.example.demoapp.model.FdViewHolder;
import com.example.demoapp.model.User;
import com.example.demoapp.repository.FdRepository;
import com.example.demoapp.model.FixedDeposit;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.time.LocalDate;
import java.util.List;

public class RecyclerFixedDepositAdapter extends RecyclerView.Adapter<FdViewHolder> {


    private Context context;
    private List<FixedDeposit> fixedDepositList;

    private int expandedPosition = -1; // Initially, no item is expanded


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
        holder.numberView.setText(String.valueOf(fixedDeposit.getNumber()));
        holder.maturityAmountView.setText("\u20B9"+ fixedDeposit.getMaturityAmount());
        holder.amountValueView.setText("\u20B9"+ fixedDeposit.getAmount());
        holder.tenureValueView.setText(fixedDeposit.getTenure() +" "+"days");

        holder.rateView.setText(fixedDeposit.getRate() + "%");
        int daysLeft = fixedDeposit.getDaysLeft();
        int totalTenure = fixedDeposit.getTenure();
        int maxProgressBarValue = 100;
        // Calculate progress based on the remaining days
        int normalizedProgress = (int) ((double) daysLeft / totalTenure * maxProgressBarValue);
        // Set the progress and color based on the normalized progress
        holder.progressBarDaysLeft.setProgress(normalizedProgress);
        int colorForProgress = getColorForProgress(normalizedProgress);
        holder.daysLeftView.setText(String.valueOf(fixedDeposit.getDaysLeft()));
        holder.daysLeftView.setTextColor(colorForProgress);
        holder.progressBarDaysLeft.setProgressTintList(ColorStateList.valueOf(colorForProgress));

        // Set visibility based on the expanded position
        holder.expandableLayout.setVisibility(position == expandedPosition ? View.VISIBLE : View.GONE);

        holder.fdCardView.setOnClickListener(view -> {
            if (position != expandedPosition) {
                // Collapse the currently expanded item
                notifyItemChanged(expandedPosition);
                expandedPosition = holder.getAdapterPosition();
            }

            if (holder.expandableLayout.getVisibility() == View.VISIBLE) {
                holder.expandableLayout.animate()
                        .alpha(0f)
                        .setDuration(500) // Animation duration
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .withEndAction(() -> holder.expandableLayout.setVisibility(View.GONE))
                        .start();
            } else {
                holder.expandableLayout.setAlpha(0f);
                holder.expandableLayout.setVisibility(View.VISIBLE);
                holder.expandableLayout.animate()
                        .alpha(1f)
                        .setDuration(500) // Animation duration
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .start();

                LocalDate createdDate = fixedDeposit.getCreatedDate();
                LocalDate endDate = fixedDeposit.getEndDate();
                holder.startDateView.setText(createdDate.getYear()+"-"+String.format("%02d", createdDate.getMonthValue())+"-"+
                        String.format("%02d", createdDate.getDayOfMonth()));
                holder.endDateView.setText(endDate.getYear()+"-"+String.format("%02d", endDate.getMonthValue())+"-"+
                        String.format("%02d", endDate.getDayOfMonth()));
                holder.notesValueView.setText(fixedDeposit.getNotes());
                holder.bankAddressView.setText(fixedDeposit.getBankWithAddress());
            }
        });

        // Set click listener for the entire item view
        //holder.itemView.setOnClickListener(v -> removeItem(holder.getAdapterPosition(), new FdRepository(context)));

        holder.itemView.setOnLongClickListener(v -> {
            removeItem(holder.getAdapterPosition(), new FdRepository(context));
            return true; // important: tells system the long click was handled
        });
    }


    private int getColorForProgress(int progress) {
        if (progress >= 75) {
            // Return color for progress >= 75%
            return ContextCompat.getColor(context, R.color.green);
        } else if (progress >= 50) {
            // Return color for progress >= 50%
            return ContextCompat.getColor(context, R.color.mediumProgressColor);
        } else if (progress >= 25) {
            // Return color for progress >= 25%
            return ContextCompat.getColor(context, R.color.lowProgressColor);
        } else {
            // Return color for progress < 25%
            return ContextCompat.getColor(context, R.color.veryLowProgressColor);
        }
    }

    @Override
    public int getItemCount() {
        return fixedDepositList.size();
    }

    public void removeAt(int position) {
        fixedDepositList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, fixedDepositList.size());
    }

    public void removeItem(int position, FdRepository fdRepository) {
        FixedDeposit fixedDeposit = fixedDepositList.get(position);
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle("Manage Fixed Deposit");
        builder.setMessage("Choose an action:");

        builder.setPositiveButton("Delete", (dialog, which) -> {
            // Call the repository to delete the FD
            if (fdRepository != null) {
                fdRepository.deleteFixedDeposit(fixedDeposit);
                removeAt(position);
                Toast.makeText(context, "Fixed Deposit Removed Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Something Went Wrong! Try Again Later", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Update", (dialog, which) -> {
            // Start the CreateFdLayout activity for updating
            Intent intent = new Intent(context, CreateFdLayout.class);
            intent.putExtra("fixedDeposit", fixedDeposit);
            MainActivity mainActivity = (MainActivity) context;
            intent.putExtra("loggedInUser",mainActivity.getLoggedInUser());
            context.startActivity(intent);
        });



        builder.setNeutralButton("Cancel", (dialog, which) -> {
            // Do nothing or dismiss the dialog
        });

        builder.show();

    }


    public FixedDeposit getFdAtPosition(int position) {
        return fixedDepositList.get(position);
    }


    public void setData(List<FixedDeposit> fixedDepositList) {
        this.fixedDepositList = fixedDepositList;
        notifyDataSetChanged();
    }
}
