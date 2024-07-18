package com.example.financecalculator.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AmortizationSchedule implements Parcelable {
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

    protected AmortizationSchedule(Parcel in) {
        paymentNumber = in.readInt();
        beginningBalance = in.readDouble();
        monthlyRepayment = in.readDouble();
        interestPaid = in.readDouble();
        principalPaid = in.readDouble();
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
