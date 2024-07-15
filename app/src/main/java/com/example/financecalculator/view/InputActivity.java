package com.example.financecalculator.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.financecalculator.R;
import com.example.financecalculator.databinding.ActivityInputBinding;
import com.example.financecalculator.model.Loan;
import com.example.financecalculator.viewmodel.LoanViewModel;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class InputActivity extends AppCompatActivity {
    private LoanViewModel loanViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInputBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_input);
        loanViewModel = new ViewModelProvider(this).get(LoanViewModel.class);
        binding.setViewModel(loanViewModel);
        binding.setLifecycleOwner(this);

        EditText etPrincipal = findViewById(R.id.et_principal);
        EditText etInterestRate = findViewById(R.id.et_interest_rate);
        EditText etTenure = findViewById(R.id.et_tenure);
        Button btnCalculate = findViewById(R.id.btn_calculate);

        btnCalculate.setOnClickListener(v -> {
            double principal = Double.parseDouble(etPrincipal.getText().toString());
            double interestRate = Double.parseDouble(etInterestRate.getText().toString());
            int tenure = Integer.parseInt(etTenure.getText().toString());

            loanViewModel.setLoan(new Loan(principal, interestRate, tenure, LocalDateTime.now(ZoneId.systemDefault())));
            loanViewModel.calcMthlyInstalment();

            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
            startActivity(intent);
        });
    }
}
