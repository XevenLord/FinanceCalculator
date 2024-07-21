package com.example.financecalculator.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmortizationSchedule implements Parcelable {
    private int paymentNumber;
    private double beginningBalance;
    private double monthlyRepayment;
    private double interestPaid;
    private double principalPaid;
    private String paymentDate;

    public AmortizationSchedule(int paymentNumber, double beginningBalance, double monthlyRepayment, double interestPaid, double principalPaid, String paymentDate) {
        this.paymentNumber = paymentNumber;
        this.beginningBalance = beginningBalance;
        this.monthlyRepayment = monthlyRepayment;
        this.interestPaid = interestPaid;
        this.principalPaid = principalPaid;
        this.paymentDate = paymentDate;
    }

    public AmortizationSchedule(int paymentNumber, double beginningBalance, double monthlyRepayment, double interestPaid, double principalPaid) {
        this.paymentNumber = paymentNumber;
        this.beginningBalance = roundToTwoDecimals(beginningBalance);
        this.monthlyRepayment = roundToTwoDecimals(monthlyRepayment);
        this.interestPaid = roundToTwoDecimals(interestPaid);
        this.principalPaid = roundToTwoDecimals(principalPaid);
    }

    protected AmortizationSchedule(Parcel in) {
        paymentNumber = in.readInt();
        beginningBalance = roundToTwoDecimals(in.readDouble());
        monthlyRepayment = roundToTwoDecimals(in.readDouble());
        interestPaid = roundToTwoDecimals(in.readDouble());
        principalPaid = roundToTwoDecimals(in.readDouble());
        paymentDate = in.readString();
    }

    public static final Creator<AmortizationSchedule> CREATOR = new Creator<AmortizationSchedule>() {
        @Override
        public AmortizationSchedule createFromParcel(Parcel in) {
            return new AmortizationSchedule(in);
        }

        @Override
        public AmortizationSchedule[] newArray(int size) {
            return new AmortizationSchedule[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(paymentNumber);
        dest.writeDouble(beginningBalance);
        dest.writeDouble(monthlyRepayment);
        dest.writeDouble(interestPaid);
        dest.writeDouble(principalPaid);
        dest.writeString(paymentDate);
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

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentNumber(int paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public void setBeginningBalance(double beginningBalance) {
        this.beginningBalance = beginningBalance;
    }

    public void setMonthlyRepayment(double monthlyRepayment) {
        this.monthlyRepayment = monthlyRepayment;
    }

    public void setInterestPaid(double interestPaid) {
        this.interestPaid = interestPaid;
    }

    public void setPrincipalPaid(double principalPaid) {
        this.principalPaid = principalPaid;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    private double roundToTwoDecimals(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
