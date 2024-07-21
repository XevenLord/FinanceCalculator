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

public class PersonalLoanFragment extends Fragment {

    private TextView tvPersonalMonthlyInstalment;
    private TextView tvPersonalTotalAmount;
    private TextView tvPersonalLastPaymentDate;
    private TextView tvInterestPaid;
    private EditText etMonthYear;
    private RecyclerView recyclerView;
    private AmortizationScheduleAdapter adapter;
    private ArrayList<AmortizationSchedule> personalLoanSchedule;
    private Button btnToggleSchedule;
    private Button btnLookupInterest;
    private LoanViewModel loanViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_loan, container, false);

        tvPersonalMonthlyInstalment = view.findViewById(R.id.tv_personal_monthly_instalment);
        tvPersonalTotalAmount = view.findViewById(R.id.tv_personal_total_amount);
        tvPersonalLastPaymentDate = view.findViewById(R.id.tv_personal_last_payment_date);
        tvInterestPaid = view.findViewById(R.id.tv_interest_paid);
        etMonthYear = view.findViewById(R.id.et_month_year);
        recyclerView = view.findViewById(R.id.recycler_view);
        btnToggleSchedule = view.findViewById(R.id.btn_toggle_schedule);
        btnLookupInterest = view.findViewById(R.id.btn_lookup_interest);

        loanViewModel = new ViewModelProvider(requireActivity()).get(LoanViewModel.class);

        if (getArguments() != null) {
            double personalMonthlyInstalment = getArguments().getDouble("personalMonthlyInstalment");
            double personalTotalAmount = getArguments().getDouble("personalTotalAmount");
            String personalLastPaymentDate = getArguments().getString("personalLastPaymentDate");
            personalLoanSchedule = getArguments().getParcelableArrayList("personalLoanSchedule");

            tvPersonalMonthlyInstalment.setText(String.format("%.2f", personalMonthlyInstalment));
            tvPersonalTotalAmount.setText(String.format("%.2f", personalTotalAmount));
            tvPersonalLastPaymentDate.setText(personalLastPaymentDate);
        }

        adapter = new AmortizationScheduleAdapter(personalLoanSchedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Set the button click listener for toggling schedule
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

        // Set the button click listener for looking up interest paid
        btnLookupInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String monthYear = etMonthYear.getText().toString().trim();
                if (!monthYear.isEmpty()) {
                    Double interestPaid = loanViewModel.getInterestPaidForMonthYear(monthYear, personalLoanSchedule);
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
