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

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize LoanViewModel using ViewModelProvider
        loanViewModel = new ViewModelProvider(requireActivity()).get(LoanViewModel.class);

        // Initialize TextViews
        TextView tvPersonalMonthlyInstallment = view.findViewById(R.id.tv_personal_monthly_installment);
        TextView tvPersonalTotalAmount = view.findViewById(R.id.tv_personal_total_amount);
        TextView tvPersonalLastPaymentDate = view.findViewById(R.id.tv_personal_last_payment_date);

        // Observe LiveData from LoanViewModel
        loanViewModel.getPersonalMonthlyInstalment().observe(getViewLifecycleOwner(), installment -> {
            Log.d("Personal", String.valueOf(installment));
            tvPersonalMonthlyInstallment.setText(String.valueOf(installment));
        });

        loanViewModel.getPersonalTotalAmt().observe(getViewLifecycleOwner(), totalAmount -> {
            tvPersonalTotalAmount.setText(String.valueOf(totalAmount));
        });

        loanViewModel.getPersonalLastPaymentDate().observe(getViewLifecycleOwner(), lastPaymentDate -> {
            tvPersonalLastPaymentDate.setText(lastPaymentDate);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_loan_result, container, false);
    }
}