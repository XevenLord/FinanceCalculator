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

public class HousingLoanFragment extends Fragment {

    private double housingMonthlyInstalment;
    private double housingTotalAmount;
    private String housingLastPaymentDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_housing_loan, container, false);

        if (getArguments() != null) {
            housingMonthlyInstalment = getArguments().getDouble("housingMonthlyInstalment");
            housingTotalAmount = getArguments().getDouble("housingTotalAmount");
            housingLastPaymentDate = getArguments().getString("housingLastPaymentDate");
        }

        TextView tvHousingMonthlyInstallment = view.findViewById(R.id.tv_housing_monthly_installment);
        TextView tvHousingTotalAmount = view.findViewById(R.id.tv_housing_total_amount);
        TextView tvHousingLastPaymentDate = view.findViewById(R.id.tv_housing_last_payment_date);

        tvHousingMonthlyInstallment.setText(String.valueOf(housingMonthlyInstalment));
        tvHousingTotalAmount.setText(String.valueOf(housingTotalAmount));
        tvHousingLastPaymentDate.setText(housingLastPaymentDate);

        return view;
    }
}
