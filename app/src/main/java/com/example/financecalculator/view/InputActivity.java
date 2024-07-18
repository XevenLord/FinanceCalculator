package com.example.financecalculator.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.financecalculator.R;
import com.example.financecalculator.model.Loan;
import com.example.financecalculator.viewmodel.LoanViewModel;
import com.example.financecalculator.viewmodel.UserViewModel;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class InputActivity extends AppCompatActivity {
    private LoanViewModel loanViewModel;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // Initialize LoanViewModel using ViewModelProvider
        loanViewModel = new ViewModelProvider(this).get(LoanViewModel.class);

        // Example of initializing UserViewModel, adjust as per your actual implementation
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        EditText etPrincipal = findViewById(R.id.et_principal);
        EditText etInterestRate = findViewById(R.id.et_interest_rate);
        EditText etTenure = findViewById(R.id.et_tenure);
        Button btnCalculate = findViewById(R.id.btn_calculate);
        Button btnClear = findViewById(R.id.btn_clear);

        btnCalculate.setOnClickListener(v -> {
            // Parse input values
            double principal = Double.parseDouble(etPrincipal.getText().toString());
            double interestRate = Double.parseDouble(etInterestRate.getText().toString());
            int tenure = Integer.parseInt(etTenure.getText().toString());

            // Create Loan object
            Loan loan = new Loan(principal, interestRate, tenure, LocalDateTime.now(ZoneId.systemDefault()));

            // Set Loan object to ViewModel
            loanViewModel.setLoan(loan);

            // Calculate monthly installments
            loanViewModel.calcMthlyInstalment();

            // Start ResultActivity
            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
            startActivity(intent);
        });

        btnClear.setOnClickListener(v -> {
            // Clear EditText fields
            etPrincipal.setText("");
            etInterestRate.setText("");
            etTenure.setText("");
        });
    }
}
