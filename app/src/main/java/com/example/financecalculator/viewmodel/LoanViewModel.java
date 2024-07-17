package com.example.financecalculator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.financecalculator.model.Loan;
import com.example.financecalculator.model.UserDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LoanViewModel extends AndroidViewModel {

    private MutableLiveData<Loan> loan = new MutableLiveData<>();
    private MutableLiveData<Double> housingMonthlyInstalment = new MutableLiveData<>();
    private MutableLiveData<Double> personalMonthlyInstalment = new MutableLiveData<>();
    private MutableLiveData<Double> housingTotalAmt = new MutableLiveData<>();
    private MutableLiveData<Double> personalTotalAmt = new MutableLiveData<>();
    private MutableLiveData<String> personalLastPaymentDate = new MutableLiveData<>();
    private MutableLiveData<String> housingLastPaymentDate = new MutableLiveData<>();
    private MutableLiveData<UserDto> user = new MutableLiveData<>();
    private MutableLiveData<Integer> personalTenure = new MutableLiveData<>();
    private MutableLiveData<Integer> housingTenure = new MutableLiveData<>();
    private MutableLiveData<Calendar> startLoanPaymentDate = new MutableLiveData<>(Calendar.getInstance());

    public LoanViewModel(@NonNull Application application) {
        super(application);
    }

    // Method to calculate monthly installments
    public void calcMthlyInstalment() {
        Loan loanData = loan.getValue();
        UserDto userData = user.getValue();
        Calendar startDate = startLoanPaymentDate.getValue();
        if (loanData != null && userData != null && startDate != null) {
            double principal = loanData.getPrin();
            double rate = loanData.getIntrstRate() / 12 / 100;
            int pTenure = loanData.getTenure();
            int hTenure = loanData.getTenure();

            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int age = currentYear - userData.getBirthYear();

            pTenure = Math.min(pTenure, Math.min(10, 60 - age));
            hTenure = Math.min(hTenure, Math.min(35, 70 - age));

            // Store the number of repayment
            personalTenure.setValue(pTenure);
            housingTenure.setValue(hTenure);

            // Calculate personal and housing loan monthly installment
            personalMonthlyInstalment.setValue(personalLoanInstalment(principal, rate, pTenure));
            housingMonthlyInstalment.setValue(housingLoanInstalment(principal, rate, hTenure));

            // Calculate total amounts
            personalTotalAmt.setValue(roundToTwoDecimalPlaces(personalMonthlyInstalment.getValue() * pTenure));
            housingTotalAmt.setValue(roundToTwoDecimalPlaces(housingMonthlyInstalment.getValue() * hTenure));

            // Calculate last payment dates
            personalLastPaymentDate.setValue(calculateLastPaymentDate(startDate, pTenure));
            housingLastPaymentDate.setValue(calculateLastPaymentDate(startDate, hTenure));
        }
    }

    private double personalLoanInstalment(double p, double r, int n) {
        return roundToTwoDecimalPlaces((p * (1 + r * n)) / n);
    }

    private double housingLoanInstalment(double p, double r, int n) {
        return roundToTwoDecimalPlaces((p * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1));
    }

    private double roundToTwoDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private String calculateLastPaymentDate(Calendar startDate, int tenure) {
        Calendar calendar = (Calendar) startDate.clone();
        calendar.add(Calendar.MONTH, tenure);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    public LiveData<Double> getHousingMonthlyInstalment() {
        return housingMonthlyInstalment;
    }

    public LiveData<Double> getPersonalMonthlyInstalment() {
        return personalMonthlyInstalment;
    }

    public LiveData<Double> getHousingTotalAmt() {
        return housingTotalAmt;
    }

    public LiveData<Double> getPersonalTotalAmt() {
        return personalTotalAmt;
    }

    public LiveData<String> getHousingLastPaymentDate() {
        return housingLastPaymentDate;
    }

    public LiveData<String> getPersonalLastPaymentDate() {
        return personalLastPaymentDate;
    }

    public void setLoan(Loan loan) {
        this.loan.setValue(loan);
    }

    public LiveData<Loan> getLoan() {
        return loan;
    }

    public void setUser(UserDto user) {
        this.user.setValue(user);
    }

    public LiveData<UserDto> getUser() {
        return user;
    }

    public LiveData<Calendar> getStartLoanPaymentDate() {
        return startLoanPaymentDate;
    }

    public void setStartLoanPaymentDate(Calendar startLoanPaymentDate) {
        this.startLoanPaymentDate.setValue(startLoanPaymentDate);
        // Recalculate installments and last payment dates when start date changes
        calcMthlyInstalment();
    }
}
