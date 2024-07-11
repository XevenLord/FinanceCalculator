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

    public void calcHousingMthlyInstalment() {
        Loan loanData = loan.getValue();
        if (loanData != null) {
            double principal = loanData.getPrin();
            double rate = loanData.getIntrstRate() / 12 / 100;
            int tenure = loanData.getTenure();

            double instalment = (principal * rate * Math.pow(1 + rate, tenure)) / (Math.pow(1 + rate, tenure) - 1);
            housingMonthlyInstalment.setValue(roundToTwoDecimalPlaces(instalment));
        }
    }

    // isHousing true ->  Housing loan calculation
    // else -> Personal loan calculation
    public void calcMthlyInstalment() {
        Loan loanData = loan.getValue();
        if (loanData != null) {
            double principal = loanData.getPrin();
            double rate = loanData.getIntrstRate() / 12 / 100;
            int tenure = loanData.getTenure();

            double instalment;
            instalment = (principal * rate * Math.pow(1 + rate, tenure)) / (Math.pow(1 + rate, tenure) - 1);
            housingMonthlyInstalment.setValue(roundToTwoDecimalPlaces(instalment));
            instalment = (principal * (1 + rate * tenure)) / tenure;
            personalMonthlyInstalment.setValue(roundToTwoDecimalPlaces(instalment));
        }
    }

    public void calcPersonalMthlyInstalment() {
        Loan loanData = loan.getValue();
        if (loanData != null) {
            double principal = loanData.getPrin();
            double rate = loanData.getIntrstRate() / 12 / 100;
            int tenure = loanData.getTenure();

            double instalment = (principal * (1 + rate * tenure)) / tenure;
            personalMonthlyInstalment.setValue(roundToTwoDecimalPlaces(instalment));
        }
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
