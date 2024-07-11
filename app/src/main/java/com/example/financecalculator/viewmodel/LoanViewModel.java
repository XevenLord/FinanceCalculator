package com.example.financecalculator.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.financecalculator.model.Loan;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LoanViewModel extends ViewModel {

    private MutableLiveData<Loan> loan = new MutableLiveData<>();
    private MutableLiveData<Double> housingMonthlyInstalment = new MutableLiveData<>();
    private MutableLiveData<Double> personalMonthlyInstalment = new MutableLiveData<>();

    // isHousing true ->  Housing loan calculation
    // else -> Personal loan calculation
    public void calcMthlyInstalment() {
        Loan loanData = loan.getValue();
        if (loanData != null) {
            double principal = loanData.getPrin();
            double rate = loanData.getIntrstRate() / 12 / 100;
            int tenure = loanData.getTenure();

            // calculate personal and housing loan monthly instalment
            // then set into live data
            personalMonthlyInstalment.setValue(personalLoanInstalment(principal, rate, tenure));
            housingMonthlyInstalment.setValue(housingLoanInstalment(principal, rate, tenure));
        }
    }

    private double personalLoanInstalment(double p, double r, int n) {
        return roundToTwoDecimalPlaces((p * (1 + r * n)) / n);
    }

    private double housingLoanInstalment(double p, double r, int n) {
        return roundToTwoDecimalPlaces((p * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1));
    }


    private double roundToTwoDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public LiveData<Double> getHousingMonthlyInstalment() {
        return housingMonthlyInstalment;
    }

    public LiveData<Double> getPersonalMonthlyInstalment() {
        return personalMonthlyInstalment;
    }

    public void setLoan(Loan loan) {
        this.loan.setValue(loan);
    }

}
