package com.example.financecalculator.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.financecalculator.R;
import com.example.financecalculator.model.Loan;
import com.example.financecalculator.viewmodel.LoanViewModel;
import com.example.financecalculator.store.ViewModelStoreHolder;
import com.example.financecalculator.viewmodel.UserViewModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

public class InputActivity extends AppCompatActivity {
    private LoanViewModel loanViewModel;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        loanViewModel = new ViewModelProvider(
                ViewModelStoreHolder.getInstance(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
        ).get(LoanViewModel.class);

        userViewModel = new ViewModelProvider(
                ViewModelStoreHolder.getInstance(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
        ).get(UserViewModel.class);

        EditText etPrincipal = findViewById(R.id.et_principal);
        EditText etInterestRate = findViewById(R.id.et_interest_rate);
        EditText etTenure = findViewById(R.id.et_tenure);
        Button btnCalculate = findViewById(R.id.btn_calculate);
        Button btnClear = findViewById(R.id.btn_clear);

        btnCalculate.setOnClickListener(v -> {
            double principal = Double.parseDouble(etPrincipal.getText().toString());
            double interestRate = Double.parseDouble(etInterestRate.getText().toString());
            int tenure = Integer.parseInt(etTenure.getText().toString());

            Loan loan = new Loan(principal, interestRate, tenure, LocalDateTime.now(ZoneId.systemDefault()));
            loanViewModel.setLoan(loan);
            loanViewModel.calcMthlyInstalment();

            // Observe the LiveData values
            loanViewModel.getPersonalMonthlyInstalment().observe(this, new Observer<Double>() {
                @Override
                public void onChanged(Double personalMonthlyInstalment) {
                    loanViewModel.getHousingMonthlyInstalment().observe(InputActivity.this, new Observer<Double>() {
                        @Override
                        public void onChanged(Double housingMonthlyInstalment) {
                            loanViewModel.getPersonalTotalAmt().observe(InputActivity.this, new Observer<Double>() {
                                @Override
                                public void onChanged(Double personalTotalAmount) {
                                    loanViewModel.getHousingTotalAmt().observe(InputActivity.this, new Observer<Double>() {
                                        @Override
                                        public void onChanged(Double housingTotalAmount) {
                                            loanViewModel.getPersonalLastPaymentDate().observe(InputActivity.this, new Observer<String>() {
                                                @Override
                                                public void onChanged(String personalLastPaymentDate) {
                                                    loanViewModel.getHousingLastPaymentDate().observe(InputActivity.this, new Observer<String>() {
                                                        @Override
                                                        public void onChanged(String housingLastPaymentDate) {
                                                            loanViewModel.getPersonalLoanSchedule().observe(InputActivity.this, personalLoanSchedules -> {
                                                                loanViewModel.getHousingLoanSchedule().observe(InputActivity.this, housingLoanSchedules -> {
                                                                    Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                                                                    intent.putExtra("personalMonthlyInstalment", personalMonthlyInstalment);
                                                                    intent.putExtra("housingMonthlyInstalment", housingMonthlyInstalment);
                                                                    intent.putExtra("personalTotalAmount", personalTotalAmount);
                                                                    intent.putExtra("housingTotalAmount", housingTotalAmount);
                                                                    intent.putExtra("personalLastPaymentDate", personalLastPaymentDate);
                                                                    intent.putExtra("housingLastPaymentDate", housingLastPaymentDate);
                                                                    intent.putExtra("personalLoanSchedule", new ArrayList<>(personalLoanSchedules));
                                                                    intent.putExtra("housingLoanSchedule", new ArrayList<>(housingLoanSchedules));
                                                                    startActivity(intent);
                                                                });
                                                            });
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        });

        btnClear.setOnClickListener(v -> {
            etPrincipal.setText("");
            etInterestRate.setText("");
            etTenure.setText("");
        });
    }
}
