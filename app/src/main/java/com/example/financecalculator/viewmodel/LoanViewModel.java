package com.example.financecalculator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.financecalculator.model.Loan;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LoanViewModel extends AndroidViewModel {

    private MutableLiveData<Loan> loan = new MutableLiveData<>();
    private MutableLiveData<Double> housingMonthlyInstalment = new MutableLiveData<>();
    private MutableLiveData<Double> personalMonthlyInstalment = new MutableLiveData<>();

    public LoanViewModel(@NonNull Application application) {
        super(application);
    }

    // Method to calculate monthly installments
    public void calcMthlyInstalment() {
        Loan loanData = loan.getValue();
        if (loanData != null) {
            double principal = loanData.getPrin();
            double rate = loanData.getIntrstRate() / 12 / 100;
            int tenure = loanData.getTenure();

            // Calculate personal and housing loan monthly installment
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

    public LiveData<Loan> getLoan() {
        return loan;
    }
}
