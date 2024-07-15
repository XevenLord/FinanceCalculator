package com.example.financecalculator.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.financecalculator.R;
import com.example.financecalculator.viewmodel.LoanViewModel;
import com.example.financecalculator.store.ViewModelStoreHolder;

public class ResultActivity extends AppCompatActivity {
    private LoanViewModel loanViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        loanViewModel = new ViewModelProvider(
                ViewModelStoreHolder.getInstance(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
        ).get(LoanViewModel.class);

        TextView tvPersonalMonthlyInstallment = findViewById(R.id.tv_personal_monthly_installment);
        TextView tvHousingMonthlyInstallment = findViewById(R.id.tv_housing_monthly_installment);
        Button btnBack = findViewById(R.id.btn_back);

        loanViewModel.getPersonalMonthlyInstalment().observe(this, installment -> {
            tvPersonalMonthlyInstallment.setText("Personal Loan Monthly Installment: " + installment);
        });

        loanViewModel.getHousingMonthlyInstalment().observe(this, installment -> {
            tvHousingMonthlyInstallment.setText("Housing Loan Monthly Installment: " + installment);
        });

        btnBack.setOnClickListener(v -> {
            finish(); // Close this activity and return to InputActivity
        });
    }
}
