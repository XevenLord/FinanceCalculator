package com.example.financecalculator.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.financecalculator.R;
import com.example.financecalculator.model.AmortizationSchedule;
import com.example.financecalculator.viewmodel.LoanViewModel;
import com.example.financecalculator.store.ViewModelStoreHolder;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

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

        String loanType = getIntent().getStringExtra("loanType");
        double personalMonthlyInstalment = getIntent().getDoubleExtra("personalMonthlyInstalment", 0);
        double housingMonthlyInstalment = getIntent().getDoubleExtra("housingMonthlyInstalment", 0);
        double personalTotalAmount = getIntent().getDoubleExtra("personalTotalAmount", 0);
        double housingTotalAmount = getIntent().getDoubleExtra("housingTotalAmount", 0);
        String personalLastPaymentDate = getIntent().getStringExtra("personalLastPaymentDate");
        String housingLastPaymentDate = getIntent().getStringExtra("housingLastPaymentDate");
        ArrayList<AmortizationSchedule> personalLoanSchedule = getIntent().getParcelableArrayListExtra("personalLoanSchedule");
        ArrayList<AmortizationSchedule> housingLoanSchedule = getIntent().getParcelableArrayListExtra("housingLoanSchedule");

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        Button btnBackToInput = findViewById(R.id.btn_back_to_input);

        btnBackToInput.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, InputActivity.class);
            startActivity(intent);
            finish(); // Optional: finish the current activity to remove it from the back stack
        });

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                Bundle bundle = new Bundle();

                if ("Personal".equals(loanType)) {
                    fragment = new PersonalLoanFragment();
                    bundle.putDouble("personalMonthlyInstalment", personalMonthlyInstalment);
                    bundle.putDouble("personalTotalAmount", personalTotalAmount);
                    bundle.putString("personalLastPaymentDate", personalLastPaymentDate);
                    bundle.putParcelableArrayList("personalLoanSchedule", personalLoanSchedule);
                } else if ("Housing".equals(loanType)) {
                    fragment = new HousingLoanFragment();
                    bundle.putDouble("housingMonthlyInstalment", housingMonthlyInstalment);
                    bundle.putDouble("housingTotalAmount", housingTotalAmount);
                    bundle.putString("housingLastPaymentDate", housingLastPaymentDate);
                    bundle.putParcelableArrayList("housingLoanSchedule", housingLoanSchedule);
                }

                if (fragment != null) {
                    fragment.setArguments(bundle);
                }

                return fragment;
            }

            @Override
            public int getCount() {
                return "Personal".equals(loanType) ? 1 : 1;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                if ("Personal".equals(loanType)) {
                    return "Personal Loan";
                } else if ("Housing".equals(loanType)) {
                    return "Housing Loan";
                }
                return null;
            }
        };

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
