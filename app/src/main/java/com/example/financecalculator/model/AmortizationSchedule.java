package com.example.financecalculator.model;

public class AmortizationSchedule {
    private int paymentNumber;
    private double beginningBalance;
    private double monthlyRepayment;
    private double interestPaid;
    private double principalPaid;

    public AmortizationSchedule(int paymentNumber, double beginningBalance, double monthlyRepayment, double interestPaid, double principalPaid) {
        this.paymentNumber = paymentNumber;
        this.beginningBalance = beginningBalance;
        this.monthlyRepayment = monthlyRepayment;
        this.interestPaid = interestPaid;
        this.principalPaid = principalPaid;
    }

    public int getPaymentNumber() {
        return paymentNumber;
    }

    public double getBeginningBalance() {
        return beginningBalance;
    }

    public double getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public double getInterestPaid() {
        return interestPaid;
    }

    public double getPrincipalPaid() {
        return principalPaid;
    }
}