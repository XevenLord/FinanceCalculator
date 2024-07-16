package com.example.financecalculator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.financecalculator.model.AmortizationSchedule;
import com.example.financecalculator.model.Loan;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoanViewModel extends AndroidViewModel {

    private MutableLiveData<Loan> loan = new MutableLiveData<>();
    private MutableLiveData<Double> housingMonthlyInstalment = new MutableLiveData<>();
    private MutableLiveData<Double> personalMonthlyInstalment = new MutableLiveData<>();
    private MutableLiveData<Double> housingTotalAmt = new MutableLiveData<>();
    private MutableLiveData<Double> personalTotalAmt = new MutableLiveData<>();
    private MutableLiveData<Date> lastPaymentDate = new MutableLiveData<>();
    private MutableLiveData<List<AmortizationSchedule>> housingAmortizationSchedule = new MutableLiveData<>();
    private MutableLiveData<List<AmortizationSchedule>> personalAmortizationSchedule = new MutableLiveData<>();

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

            // Calculate total amounts
            personalTotalAmt.setValue(roundToTwoDecimalPlaces(personalMonthlyInstalment.getValue() * tenure));
            housingTotalAmt.setValue(roundToTwoDecimalPlaces(housingMonthlyInstalment.getValue() * tenure));

            // Calculate last payment date
            lastPaymentDate.setValue(calculateLastPaymentDate(tenure));

            // Calculate amortization schedules
            housingAmortizationSchedule.setValue(calculateHousingAmortizationSchedule(principal, rate, tenure));
            personalAmortizationSchedule.setValue(calculatePersonalAmortizationSchedule(principal, rate, tenure));
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

    private Date calculateLastPaymentDate(int tenure) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, tenure);
        return calendar.getTime();
    }

    private List<AmortizationSchedule> calculateHousingAmortizationSchedule(double p, double r, int n) {
        List<AmortizationSchedule> schedule = new ArrayList<>();
        double monthlyInstalment = housingLoanInstalment(p, r, n);
        double remainingPrincipal = p;
        for (int i = 1; i <= n; i++) {
            double interest = roundToTwoDecimalPlaces(remainingPrincipal * r);
            double principalPaid = roundToTwoDecimalPlaces(monthlyInstalment - interest);
            remainingPrincipal -= principalPaid;
            schedule.add(new AmortizationSchedule(i, roundToTwoDecimalPlaces(remainingPrincipal), monthlyInstalment, interest, principalPaid));
        }
        return schedule;
    }

    private List<AmortizationSchedule> calculatePersonalAmortizationSchedule(double p, double r, int n) {
        List<AmortizationSchedule> schedule = new ArrayList<>();
        double monthlyInstalment = personalLoanInstalment(p, r, n);
        double remainingPrincipal = p;
        for (int i = 1; i <= n; i++) {
            double interest = roundToTwoDecimalPlaces(remainingPrincipal * r);
            double principalPaid = roundToTwoDecimalPlaces(monthlyInstalment - interest);
            remainingPrincipal -= principalPaid;
            schedule.add(new AmortizationSchedule(i, roundToTwoDecimalPlaces(remainingPrincipal), monthlyInstalment, interest, principalPaid));
        }
        return schedule;
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

    public LiveData<Date> getLastPaymentDate() {
        return lastPaymentDate;
    }

    public LiveData<List<AmortizationSchedule>> getHousingAmortizationSchedule() {
        return housingAmortizationSchedule;
    }

    public LiveData<List<AmortizationSchedule>> getPersonalAmortizationSchedule() {
        return personalAmortizationSchedule;
    }

    public void setLoan(Loan loan) {
        this.loan.setValue(loan);
    }

    public LiveData<Loan> getLoan() {
        return loan;
    }
}
