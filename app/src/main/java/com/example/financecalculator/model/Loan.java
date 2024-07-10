package com.example.financecalculator.model;

import java.time.LocalDateTime;

public class Loan {
    private double prin;    // principal
    private double intrstRate;  // interestRate
    private int tenure;
    private LocalDateTime stTm; // startTime

    public Loan(double prin, double intrstRate, int tenure, LocalDateTime stTm) {
        this.prin = prin;
        this.intrstRate = intrstRate;
        this.tenure = tenure;
        this.stTm = stTm;
    }

    public Loan() {
    }

    public Loan(double prin) {
        this.prin = prin;
    }

    public Loan(int tenure) {
        this.tenure = tenure;
    }

    public Loan(LocalDateTime stTm) {
        this.stTm = stTm;
    }

    public double getPrin() {
        return prin;
    }

    public double getIntrstRate() {
        return intrstRate;
    }

    public int getTenure() {
        return tenure;
    }

    public LocalDateTime getStTm() {
        return stTm;
    }

    public void setPrin(double prin) {
        this.prin = prin;
    }

    public void setIntrstRate(double intrstRate) {
        this.intrstRate = intrstRate;
    }

    public void setTenure(int tenure) {
        this.tenure = tenure;
    }

    public void setStTm(LocalDateTime stTm) {
        this.stTm = stTm;
    }

}
