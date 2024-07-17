package com.example.financecalculator.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.financecalculator.R;
import com.example.financecalculator.viewmodel.LoanViewModel;

public class PersonalLoanResultFragment extends Fragment {

    private LoanViewModel loanViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_loan_result, container, false);

        loanViewModel = new ViewModelProvider(this).get(LoanViewModel.class);

        TextView tvPersonalMonthlyInstallment = view.findViewById(R.id.tv_personal_monthly_installment);
        TextView tvPersonalTotalAmount = view.findViewById(R.id.tv_personal_total_amount);
        TextView tvPersonalLastPaymentDate = view.findViewById(R.id.tv_personal_last_payment_date);

        loanViewModel.getPersonalMonthlyInstalment().observe(getViewLifecycleOwner(), installment -> {
            Log.d("Personall", String.valueOf(installment));
            tvPersonalMonthlyInstallment.setText(String.valueOf(installment));
        });

        loanViewModel.getPersonalTotalAmt().observe(getViewLifecycleOwner(), totalAmount -> {
            tvPersonalTotalAmount.setText(String.valueOf(totalAmount));
        });

        loanViewModel.getPersonalLastPaymentDate().observe(getViewLifecycleOwner(), lastPaymentDate -> {
            tvPersonalLastPaymentDate.setText(lastPaymentDate);
        });

        return view;
    }
}