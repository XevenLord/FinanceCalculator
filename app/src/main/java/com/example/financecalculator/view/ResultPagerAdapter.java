package com.example.financecalculator.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ResultPagerAdapter extends FragmentStateAdapter {

    public ResultPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PersonalLoanResultFragment();
            case 1:
                return new HousingLoanResultFragment();
            default:
                return new PersonalLoanResultFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
