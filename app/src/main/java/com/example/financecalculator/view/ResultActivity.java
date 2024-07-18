package com.example.financecalculator.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.financecalculator.R;
import com.example.financecalculator.viewmodel.LoanViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ResultActivity extends AppCompatActivity {

    private LoanViewModel loanViewModel;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        loanViewModel = new ViewModelProvider(this).get(LoanViewModel.class);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        // Set up ViewPager with adapter
        ResultPagerAdapter adapter = new ResultPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Personal");
                    break;
                case 1:
                    tab.setText("Housing");
                    break;
            }
        }).attach();

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }
}
