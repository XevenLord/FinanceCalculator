package com.example.financecalculator.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financecalculator.R;
import com.example.financecalculator.model.AmortizationSchedule;

import java.util.List;

public class AmortizationScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<AmortizationSchedule> amortizationScheduleList;

    public AmortizationScheduleAdapter(List<AmortizationSchedule> amortizationScheduleList) {
        this.amortizationScheduleList = amortizationScheduleList;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amortization_schedule_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amortization_schedule, parent, false);
            return new AmortizationScheduleViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_ITEM) {
            AmortizationSchedule amortizationSchedule = amortizationScheduleList.get(position - 1); // Subtract 1 for header
            AmortizationScheduleViewHolder itemViewHolder = (AmortizationScheduleViewHolder) holder;
            itemViewHolder.tvPaymentNumber.setText(String.valueOf(amortizationSchedule.getPaymentNumber()));
            itemViewHolder.tvBeginningBalance.setText(String.valueOf(amortizationSchedule.getBeginningBalance()));
            itemViewHolder.tvMonthlyRepayment.setText(String.valueOf(amortizationSchedule.getMonthlyRepayment()));
            itemViewHolder.tvInterestPaid.setText(String.valueOf(amortizationSchedule.getInterestPaid()));
            itemViewHolder.tvPrincipalPaid.setText(String.valueOf(amortizationSchedule.getPrincipalPaid()));
        }
    }

    @Override
    public int getItemCount() {
        return amortizationScheduleList.size() + 1; // Add 1 for header
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
