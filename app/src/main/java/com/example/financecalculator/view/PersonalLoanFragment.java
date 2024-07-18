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

public class PersonalLoanFragment extends Fragment {

    private TextView tvPersonalMonthlyInstalment;
    private TextView tvPersonalTotalAmount;
    private TextView tvPersonalLastPaymentDate;
    private RecyclerView recyclerView;
    private AmortizationScheduleAdapter adapter;
    private ArrayList<AmortizationSchedule> personalLoanSchedule;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_loan, container, false);

        tvPersonalMonthlyInstalment = view.findViewById(R.id.tv_personal_monthly_installment);
        tvPersonalTotalAmount = view.findViewById(R.id.tv_personal_total_amount);
        tvPersonalLastPaymentDate = view.findViewById(R.id.tv_personal_last_payment_date);
        recyclerView = view.findViewById(R.id.recycler_view);

        if (getArguments() != null) {
            double personalMonthlyInstalment = getArguments().getDouble("personalMonthlyInstalment");
            double personalTotalAmount = getArguments().getDouble("personalTotalAmount");
            String personalLastPaymentDate = getArguments().getString("personalLastPaymentDate");
            personalLoanSchedule = getArguments().getParcelableArrayList("personalLoanSchedule");

            tvPersonalMonthlyInstalment.setText(String.valueOf(personalMonthlyInstalment));
            tvPersonalTotalAmount.setText(String.valueOf(personalTotalAmount));
            tvPersonalLastPaymentDate.setText(personalLastPaymentDate);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new AmortizationScheduleAdapter(personalLoanSchedule);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }
}
