package com.uet.moneymanager.util;

import java.util.Date;

public class TransactionDate {
    private Date date;
    private double moneyAmount;

    public TransactionDate(Date date, double moneyAmount) {
        this.date = date;
        this.moneyAmount = moneyAmount;
    }
    public TransactionDate(){}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
