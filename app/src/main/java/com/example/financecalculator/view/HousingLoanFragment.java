package com.example.financecalculator.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financecalculator.R;
import com.example.financecalculator.model.AmortizationSchedule;
import com.example.financecalculator.viewmodel.LoanViewModel;

import java.util.ArrayList;

public class HousingLoanFragment extends Fragment {

    private TextView tvHousingMonthlyInstalment;
    private TextView tvHousingTotalAmount;
    private TextView tvHousingLastPaymentDate;
    private TextView tvInterestPaid;
    private EditText etMonthYear;
    private RecyclerView recyclerView;
    private AmortizationScheduleAdapter adapter;
    private ArrayList<AmortizationSchedule> housingLoanSchedule;
    private Button btnToggleSchedule;
    private Button btnLookupInterest;
    private LoanViewModel loanViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_housing_loan, container, false);

        tvHousingMonthlyInstalment = view.findViewById(R.id.tv_housing_monthly_instalment);
        tvHousingTotalAmount = view.findViewById(R.id.tv_housing_total_amount);
        tvHousingLastPaymentDate = view.findViewById(R.id.tv_housing_last_payment_date);
        tvInterestPaid = view.findViewById(R.id.tv_interest_paid);
        etMonthYear = view.findViewById(R.id.et_month_year);
        recyclerView = view.findViewById(R.id.recycler_view);
        btnToggleSchedule = view.findViewById(R.id.btn_toggle_schedule);
        btnLookupInterest = view.findViewById(R.id.btn_lookup_interest);

        loanViewModel = new ViewModelProvider(requireActivity()).get(LoanViewModel.class);

        if (getArguments() != null) {
            double housingMonthlyInstalment = getArguments().getDouble("housingMonthlyInstalment");
            double housingTotalAmount = getArguments().getDouble("housingTotalAmount");
            String housingLastPaymentDate = getArguments().getString("housingLastPaymentDate");
            housingLoanSchedule = getArguments().getParcelableArrayList("housingLoanSchedule");

            tvHousingMonthlyInstalment.setText(String.format("%.2f", housingMonthlyInstalment));
            tvHousingTotalAmount.setText(String.format("%.2f", housingTotalAmount));
            tvHousingLastPaymentDate.setText(housingLastPaymentDate);
        }

        adapter = new AmortizationScheduleAdapter(housingLoanSchedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Set the button click listener
        btnToggleSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerView.getVisibility() == View.GONE) {
                    recyclerView.setVisibility(View.VISIBLE);
                    btnToggleSchedule.setText("Hide Schedule");
                } else {
                    recyclerView.setVisibility(View.GONE);
                    btnToggleSchedule.setText("Show Schedule");
                }
            }
        });

        btnLookupInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String monthYear = etMonthYear.getText().toString().trim();
                if (!monthYear.isEmpty()) {
                    Double interestPaid = loanViewModel.getInterestPaidForMonthYear(monthYear, housingLoanSchedule);
                    if (interestPaid != null) {
                        tvInterestPaid.setText("Interest Paid: " + String.format("%.2f", interestPaid));
                    } else {
                        tvInterestPaid.setText("No data found for " + monthYear);
                    }
                } else {
                    tvInterestPaid.setText("Please enter a valid month and year.");
                }
            }
        });

        return view;
    }
}