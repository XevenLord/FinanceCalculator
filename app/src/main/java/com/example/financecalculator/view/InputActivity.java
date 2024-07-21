package com.example.financecalculator.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.financecalculator.R;
import com.example.financecalculator.model.Loan;
import com.example.financecalculator.viewmodel.LoanViewModel;
import com.example.financecalculator.store.ViewModelStoreHolder;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;

public class InputActivity extends AppCompatActivity {
    private LoanViewModel loanViewModel;
    private FirebaseAuth mAuth;
    private TextView tvSelectedDate;
    private LocalDate selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        loanViewModel = new ViewModelProvider(
                ViewModelStoreHolder.getInstance(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
        ).get(LoanViewModel.class);

        EditText etPrincipal = findViewById(R.id.et_principal);
        EditText etInterestRate = findViewById(R.id.et_interest_rate);
        EditText etTenure = findViewById(R.id.et_tenure);
        Spinner spinnerLoanType = findViewById(R.id.spinner_loan_type);
        Button btnCalculate = findViewById(R.id.btn_calculate);
        Button btnClear = findViewById(R.id.btn_clear);
        Button btnSelectDate = findViewById(R.id.btn_select_date);
        Button btnFeedback = findViewById(R.id.btn_feedback);
        tvSelectedDate = findViewById(R.id.tv_selected_date);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.loan_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoanType.setAdapter(adapter);

        btnSelectDate.setOnClickListener(v -> showDatePickerDialog());

        btnCalculate.setOnClickListener(v -> {
            Log.d("DEBUG", "Enter button action");

            String principalStr = etPrincipal.getText().toString();
            String interestRateStr = etInterestRate.getText().toString();
            String tenureStr = etTenure.getText().toString();
            String loanType = spinnerLoanType.getSelectedItem().toString();
            LocalDateTime startDateTime = null;

            // Validation checks
            if (principalStr.isEmpty()) {
                etPrincipal.setError("Principal amount is required");
                return;
            }

            if (interestRateStr.isEmpty()) {
                etInterestRate.setError("Interest rate is required");
                return;
            }

            if (tenureStr.isEmpty()) {
                etTenure.setError("Tenure is required");
                return;
            }

            if (selectedDate == null) {
                Toast.makeText(InputActivity.this, "Please select a start date", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double principal = Double.parseDouble(principalStr);
                double interestRate = Double.parseDouble(interestRateStr);
                int tenure = Integer.parseInt(tenureStr);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    startDateTime = selectedDate.atStartOfDay();
                }

                Loan loan = new Loan(principal, interestRate, tenure, startDateTime);
                loanViewModel.setLoan(loan);

                if ("Personal".equals(loanType)) {
                    loanViewModel.calcPersonalMthlyInstalment();
                    observePersonalLoanData();
                } else if ("Housing".equals(loanType)) {
                    loanViewModel.calcHousingMthlyInstalment();
                    observeHousingLoanData();
                }
                Log.d("DEBUG", "Ready process");

            } catch (NumberFormatException e) {
                Toast.makeText(InputActivity.this, "Invalid input, please check your entries", Toast.LENGTH_SHORT).show();
            }
        });

        btnClear.setOnClickListener(v -> {
            etPrincipal.setText("");
            etInterestRate.setText("");
            etTenure.setText("");
            spinnerLoanType.setSelection(0);
            tvSelectedDate.setText("");
            selectedDate = null;
        });

        btnFeedback.setOnClickListener(v -> {
            Intent intent = new Intent(InputActivity.this, FeedbackActivity.class);
            startActivity(intent);
        });
    }

    private void observePersonalLoanData() {
        loanViewModel.getPersonalMonthlyInstalment().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double personalMonthlyInstalment) {
                loanViewModel.getPersonalTotalAmt().observe(InputActivity.this, new Observer<Double>() {
                    @Override
                    public void onChanged(Double personalTotalAmount) {
                        loanViewModel.getPersonalLastPaymentDate().observe(InputActivity.this, new Observer<String>() {
                            @Override
                            public void onChanged(String personalLastPaymentDate) {
                                loanViewModel.getPersonalLoanSchedule().observe(InputActivity.this, personalLoanSchedules -> {
                                    Log.d("INFO", "Ready to process Personal Loan Data");
                                    Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                                    intent.putExtra("loanType", "Personal");
                                    intent.putExtra("personalMonthlyInstalment", personalMonthlyInstalment);
                                    intent.putExtra("personalTotalAmount", personalTotalAmount);
                                    intent.putExtra("personalLastPaymentDate", personalLastPaymentDate);
                                    intent.putExtra("personalLoanSchedule", new ArrayList<>(personalLoanSchedules));
                                    startActivity(intent);
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    private void observeHousingLoanData() {
        loanViewModel.getHousingMonthlyInstalment().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double housingMonthlyInstalment) {
                loanViewModel.getHousingTotalAmt().observe(InputActivity.this, new Observer<Double>() {
                    @Override
                    public void onChanged(Double housingTotalAmount) {
                        loanViewModel.getHousingLastPaymentDate().observe(InputActivity.this, new Observer<String>() {
                            @Override
                            public void onChanged(String housingLastPaymentDate) {
                                loanViewModel.getHousingLoanSchedule().observe(InputActivity.this, housingLoanSchedules -> {
                                    Log.d("INFO", "Ready to process Housing Loan Data");
                                    Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                                    intent.putExtra("loanType", "Housing");
                                    intent.putExtra("housingMonthlyInstalment", housingMonthlyInstalment);
                                    intent.putExtra("housingTotalAmount", housingTotalAmount);
                                    intent.putExtra("housingLastPaymentDate", housingLastPaymentDate);
                                    intent.putExtra("housingLoanSchedule", new ArrayList<>(housingLoanSchedules));
                                    startActivity(intent);
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            mAuth.signOut();
            Intent intent = new Intent(InputActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                InputActivity.this,
                (view, year1, month1, dayOfMonth) -> {
                    selectedDate = LocalDate.of(year1, month1 + 1, dayOfMonth);
                    tvSelectedDate.setText(selectedDate.toString());
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}
