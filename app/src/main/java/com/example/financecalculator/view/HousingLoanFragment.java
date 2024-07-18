package com.example.financecalculator.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financecalculator.R;
import com.example.financecalculator.model.AmortizationSchedule;

import java.util.ArrayList;

public class HousingLoanFragment extends Fragment {

    private TextView tvHousingMonthlyInstalment;
    private TextView tvHousingTotalAmount;
    private TextView tvHousingLastPaymentDate;
    private RecyclerView recyclerView;
    private AmortizationScheduleAdapter adapter;
    private ArrayList<AmortizationSchedule> housingLoanSchedule;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_housing_loan, container, false);

        tvHousingMonthlyInstalment = view.findViewById(R.id.tv_housing_monthly_installment);
        tvHousingTotalAmount = view.findViewById(R.id.tv_housing_total_amount);
        tvHousingLastPaymentDate = view.findViewById(R.id.tv_housing_last_payment_date);
        recyclerView = view.findViewById(R.id.recycler_view);

        if (getArguments() != null) {
            double housingMonthlyInstalment = getArguments().getDouble("housingMonthlyInstalment");
            double housingTotalAmount = getArguments().getDouble("housingTotalAmount");
            String housingLastPaymentDate = getArguments().getString("housingLastPaymentDate");
            housingLoanSchedule = getArguments().getParcelableArrayList("housingLoanSchedule");

            tvHousingMonthlyInstalment.setText(String.valueOf(housingMonthlyInstalment));
            tvHousingTotalAmount.setText(String.valueOf(housingTotalAmount));
            tvHousingLastPaymentDate.setText(housingLastPaymentDate);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new AmortizationScheduleAdapter(housingLoanSchedule);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }
}
