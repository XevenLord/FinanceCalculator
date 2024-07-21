package com.example.financecalculator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.financecalculator.model.AmortizationSchedule;
import com.example.financecalculator.model.Loan;
import com.example.financecalculator.model.UserDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    private MutableLiveData<List<AmortizationSchedule>> personalLoanSchedule = new MutableLiveData<>();
    private MutableLiveData<List<AmortizationSchedule>> housingLoanSchedule = new MutableLiveData<>();

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

            pTenure = Math.min(pTenure, Math.min(10 * 12, (60 - age) * 12));
            hTenure = Math.min(hTenure, Math.min(35 * 12, (70 - age) * 12));

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

            // Generate Amortization Schedule
            generateAmortizationSchedule();
        }
    }

    public void calcPersonalMthlyInstalment() {
        Loan loanData = loan.getValue();
        UserDto userData = user.getValue();
        getStartDateAsCalendar();
        Calendar startDate = startLoanPaymentDate.getValue();
        if (loanData != null && userData != null && startDate != null) {
            double principal = loanData.getPrin();
            double rate = loanData.getIntrstRate() / 12 / 100;
            int tenure = loanData.getTenure();

            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int age = currentYear - userData.getBirthYear();

            tenure = Math.min(tenure, Math.min(10 * 12, (60 - age) * 12));

            // Store the number of repayment
            personalTenure.setValue(tenure);

            // Calculate personal loan monthly installment
            personalMonthlyInstalment.setValue(personalLoanInstalment(principal, rate, tenure));

            // Calculate total amount
            personalTotalAmt.setValue(roundToTwoDecimalPlaces(personalMonthlyInstalment.getValue() * tenure));

            // Calculate last payment date
            personalLastPaymentDate.setValue(calculateLastPaymentDate(startDate, tenure));

            // Generate Amortization Schedule
            generatePersonalLoanAmortizationSchedule();
        }
    }

    public void calcHousingMthlyInstalment() {
        Loan loanData = loan.getValue();
        UserDto userData = user.getValue();
        getStartDateAsCalendar();
        Calendar startDate = startLoanPaymentDate.getValue();
        if (loanData != null && userData != null && startDate != null) {
            double principal = loanData.getPrin();
            double rate = loanData.getIntrstRate() / 12 / 100;
            int tenure = loanData.getTenure();

            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int age = currentYear - userData.getBirthYear();

            tenure = Math.min(tenure, Math.min(35 * 12, (70 - age) * 12));

            // Store the number of repayment
            housingTenure.setValue(tenure);

            // Calculate housing loan monthly installment
            housingMonthlyInstalment.setValue(housingLoanInstalment(principal, rate, tenure));

            // Calculate total amount
            housingTotalAmt.setValue(roundToTwoDecimalPlaces(housingMonthlyInstalment.getValue() * tenure));

            // Calculate last payment date
            housingLastPaymentDate.setValue(calculateLastPaymentDate(startDate, tenure));

            // Generate Amortization Schedule
            generateHousingLoanAmortizationSchedule();
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }


    public void generateAmortizationSchedule() {
        Loan loanData = loan.getValue();
        List<AmortizationSchedule> pSchedule = new ArrayList<>();
        List<AmortizationSchedule> hSchedule = new ArrayList<>();
        double pPrincipal = loanData.getPrin();
        double hPrincipal = loanData.getPrin();
        double pMonthlyRepayment = personalMonthlyInstalment.getValue();
        double hMonthlyRepayment = housingMonthlyInstalment.getValue();
        double interestRate = loanData.getIntrstRate() / 100 / 12; // Monthly interest rate

        double pInterestPaid = loanData.getPrin() * interestRate;

        for (int i = 1; i <= loanData.getTenure(); i++) {
            double hInterestPaid = roundToTwoDecimalPlaces((hPrincipal * interestRate));
            double hPrincipalPaid = roundToTwoDecimalPlaces((hMonthlyRepayment - hInterestPaid));
            double pPrincipalPaid = roundToTwoDecimalPlaces((pMonthlyRepayment - pInterestPaid));
            double pBeginningBalance = roundToTwoDecimalPlaces(pPrincipal);
            double hBeginningBalance = roundToTwoDecimalPlaces(hPrincipal);

            pSchedule.add(new AmortizationSchedule(i, pBeginningBalance, pMonthlyRepayment, pInterestPaid, pPrincipalPaid));
            hSchedule.add(new AmortizationSchedule(i, hBeginningBalance, hMonthlyRepayment, hInterestPaid, hPrincipalPaid));

            pPrincipal -= roundToTwoDecimalPlaces(pPrincipalPaid);
            hPrincipal -= roundToTwoDecimalPlaces(hPrincipalPaid);
        }

        personalLoanSchedule.setValue(pSchedule);
        housingLoanSchedule.setValue(hSchedule);
    }

    public void generatePersonalLoanAmortizationSchedule() {
        Loan loanData = loan.getValue();
        List<AmortizationSchedule> pSchedule = new ArrayList<>();
        double pPrincipal = loanData.getPrin();
        double pMonthlyRepayment = personalMonthlyInstalment.getValue();
        double interestRate = loanData.getIntrstRate() / 100 / 12; // Monthly interest rate

        double pInterestPaid = loanData.getPrin() * interestRate;

        for (int i = 1; i <= loanData.getTenure(); i++) {
            double pPrincipalPaid = roundToTwoDecimalPlaces((pMonthlyRepayment - pInterestPaid));
            double pBeginningBalance = roundToTwoDecimalPlaces(pPrincipal);

            pSchedule.add(new AmortizationSchedule(i, pBeginningBalance, pMonthlyRepayment, pInterestPaid, pPrincipalPaid));

            pPrincipal -= roundToTwoDecimalPlaces(pPrincipalPaid);
        }

        personalLoanSchedule.setValue(pSchedule);
    }

    public void generateHousingLoanAmortizationSchedule() {
        Loan loanData = loan.getValue();
        List<AmortizationSchedule> hSchedule = new ArrayList<>();
        double hPrincipal = loanData.getPrin();
        double hMonthlyRepayment = housingMonthlyInstalment.getValue();
        double interestRate = loanData.getIntrstRate() / 100 / 12; // Monthly interest rate

        for (int i = 1; i <= loanData.getTenure(); i++) {
            double hInterestPaid = roundToTwoDecimalPlaces((hPrincipal * interestRate));
            double hPrincipalPaid = roundToTwoDecimalPlaces((hMonthlyRepayment - hInterestPaid));
            double hBeginningBalance = roundToTwoDecimalPlaces(hPrincipal);

            hSchedule.add(new AmortizationSchedule(i, hBeginningBalance, hMonthlyRepayment, hInterestPaid, hPrincipalPaid));

            hPrincipal -= roundToTwoDecimalPlaces(hPrincipalPaid);
        }

        housingLoanSchedule.setValue(hSchedule);
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

    public LiveData<List<AmortizationSchedule>> getPersonalLoanSchedule() {
        return personalLoanSchedule;
    }

    public LiveData<List<AmortizationSchedule>> getHousingLoanSchedule() {
        return housingLoanSchedule;
    }

    public LiveData<String> getFormattedStartLoanPaymentDate() {
        MutableLiveData<String> formattedDate = new MutableLiveData<>();
        startLoanPaymentDate.observeForever(calendar -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
            formattedDate.setValue(dateFormat.format(calendar.getTime()));
        });
        return formattedDate;
    }

    public void setStartLoanPaymentDate(Calendar startLoanPaymentDate) {
        this.startLoanPaymentDate.setValue(startLoanPaymentDate);
    }

    public void getStartDateAsCalendar() {
        Loan loanData = loan.getValue();
        Date startDate = loanData.getStartDateAsDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        startLoanPaymentDate.setValue(calendar);
    }
}
