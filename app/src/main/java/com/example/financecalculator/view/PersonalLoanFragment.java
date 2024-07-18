package com.example.financecalculator.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.financecalculator.R;

public class PersonalLoanFragment extends Fragment {

    private double personalMonthlyInstalment;
    private double personalTotalAmount;
    private String personalLastPaymentDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_loan, container, false);

        if (getArguments() != null) {
            personalMonthlyInstalment = getArguments().getDouble("personalMonthlyInstalment");
            personalTotalAmount = getArguments().getDouble("personalTotalAmount");
            personalLastPaymentDate = getArguments().getString("personalLastPaymentDate");
        }

        TextView tvPersonalMonthlyInstallment = view.findViewById(R.id.tv_personal_monthly_installment);
        TextView tvPersonalTotalAmount = view.findViewById(R.id.tv_personal_total_amount);
        TextView tvPersonalLastPaymentDate = view.findViewById(R.id.tv_personal_last_payment_date);

        tvPersonalMonthlyInstallment.setText(String.valueOf(personalMonthlyInstalment));
        tvPersonalTotalAmount.setText(String.valueOf(personalTotalAmount));
        tvPersonalLastPaymentDate.setText(personalLastPaymentDate);

        return view;
    }
}
