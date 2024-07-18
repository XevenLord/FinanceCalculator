package com.example.financecalculator.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financecalculator.R;

public class AmortizationScheduleViewHolder extends RecyclerView.ViewHolder {
    public TextView tvPaymentNumber;
    public TextView tvBeginningBalance;
    public TextView tvMonthlyRepayment;
    public TextView tvInterestPaid;
    public TextView tvPrincipalPaid;

    public AmortizationScheduleViewHolder(@NonNull View itemView) {
        super(itemView);
        tvPaymentNumber = itemView.findViewById(R.id.tv_payment_number);
        tvBeginningBalance = itemView.findViewById(R.id.tv_beginning_balance);
        tvMonthlyRepayment = itemView.findViewById(R.id.tv_monthly_repayment);
        tvInterestPaid = itemView.findViewById(R.id.tv_interest_paid);
        tvPrincipalPaid = itemView.findViewById(R.id.tv_principal_paid);
    }
}
