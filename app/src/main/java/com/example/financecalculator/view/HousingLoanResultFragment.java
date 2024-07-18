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

public class HousingLoanResultFragment extends Fragment {

    private LoanViewModel loanViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_housing_loan_result, container, false);

        // Initialize LoanViewModel using ViewModelProvider
        loanViewModel = new ViewModelProvider(requireActivity()).get(LoanViewModel.class);

        // Initialize TextViews
        TextView tvHousingMonthlyInstallment = view.findViewById(R.id.tv_housing_monthly_installment);
        TextView tvHousingTotalAmount = view.findViewById(R.id.tv_housing_total_amount);
        TextView tvHousingLastPaymentDate = view.findViewById(R.id.tv_housing_last_payment_date);

        // Log for debugging purposes
        Log.d("HousingLoanResult", "Fragment created");

        // Observe LiveData from LoanViewModel
        loanViewModel.getHousingMonthlyInstalment().observe(getViewLifecycleOwner(), installment -> {
            Log.d("HousingLoanResult", "Monthly installment updated: " + installment);
            tvHousingMonthlyInstallment.setText(String.valueOf(installment));
        });

        loanViewModel.getHousingTotalAmt().observe(getViewLifecycleOwner(), totalAmount -> {
            Log.d("HousingLoanResult", "Total amount updated: " + totalAmount);
            tvHousingTotalAmount.setText(String.valueOf(totalAmount));
        });

        loanViewModel.getHousingLastPaymentDate().observe(getViewLifecycleOwner(), lastPaymentDate -> {
            Log.d("HousingLoanResult", "Last payment date updated: " + lastPaymentDate);
            tvHousingLastPaymentDate.setText(lastPaymentDate);
        });

        return view;
    }
}
