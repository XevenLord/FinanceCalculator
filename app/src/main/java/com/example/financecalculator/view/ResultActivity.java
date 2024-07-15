package com.example.financecalculator.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.financecalculator.R;
import com.example.financecalculator.databinding.ActivityResultBinding;
import com.example.financecalculator.viewmodel.LoanViewModel;

public class ResultActivity extends AppCompatActivity {
    private LoanViewModel loanViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityResultBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_result);
        loanViewModel = new ViewModelProvider(this).get(LoanViewModel.class);
        binding.setViewModel(loanViewModel);
        binding.setLifecycleOwner(this);

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
            Intent intent = new Intent(ResultActivity.this, InputActivity.class);
            startActivity(intent);
        });
    }
}
