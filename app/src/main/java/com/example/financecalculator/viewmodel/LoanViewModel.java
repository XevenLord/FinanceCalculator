package com.example.financecalculator.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.financecalculator.model.Loan;

public class LoanViewModel extends ViewModel {

    private MutableLiveData<Loan> loan = new MutableLiveData<>();
    private MutableLiveData<Double> monthlyInstalment = new MutableLiveData<>();

    public void calcMthlyInstalment() {
        Loan loanData = loan.getValue();
        if (loanData != null) {
            double principal = loanData.getPrin();
            double rate = loanData.getIntrstRate() / 12 / 100;
            int tenure = loanData.getTenure();

            double instalment = (principal * rate * Math.pow(1 + rate, tenure)) / (Math.pow(1 + rate, tenure) - 1);
            monthlyInstalment.setValue(instalment);
        }
    }

    public LiveData<Double> getMonthlyInstalment() {
        return monthlyInstalment;
    }

    public void setLoan(Loan loan) {
        this.loan.setValue(loan);
    }

}
