package com.example.financecalculator.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.financecalculator.R;
import com.example.financecalculator.databinding.ActivityMainBinding;
import com.example.financecalculator.model.Loan;
import com.example.financecalculator.viewmodel.LoanViewModel;

import com.google.firebase.FirebaseApp;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class MainActivity extends AppCompatActivity {
    private LoanViewModel loanViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        loanViewModel = new ViewModelProvider(this).get(LoanViewModel.class);
        binding.setViewModel(loanViewModel);
        binding.setLifecycleOwner(this);

        EditText etPrincipal = findViewById(R.id.et_principal);
        EditText etInterestRate = findViewById(R.id.et_interest_rate);
        EditText etTenure = findViewById(R.id.et_tenure);
        Button btnCalculate = findViewById(R.id.btn_calculate);
        TextView tvPersonalMonthlyInstallment = findViewById(R.id.tv_personal_monthly_installment);
        TextView tvHousingMonthlyInstallment = findViewById(R.id.tv_housing_monthly_installment);

        btnCalculate.setOnClickListener(v -> {
            double principal = Double.parseDouble(etPrincipal.getText().toString());
            double interestRate = Double.parseDouble(etInterestRate.getText().toString());
            int tenure = Integer.parseInt(etTenure.getText().toString());

            Loan loan = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                loan = new Loan(principal, interestRate, tenure, LocalDateTime.now(ZoneId.systemDefault()));
            }
            loanViewModel.setLoan(loan);
            loanViewModel.calcMthlyInstalment();
        });
        loanViewModel.getPersonalMonthlyInstalment().observe(this, installment -> {
            tvPersonalMonthlyInstallment.setText("Personal Loan Monthly Installment: " + installment);
        });
        loanViewModel.getHousingMonthlyInstalment().observe(this, installment -> {
            tvHousingMonthlyInstallment.setText("Housing Loan Monthly Installment: " + installment);
        });

    }
}