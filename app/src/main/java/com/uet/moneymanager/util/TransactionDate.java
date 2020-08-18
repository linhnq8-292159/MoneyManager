package com.uet.moneymanager.util;

import java.util.Date;

public class TransactionDate {
    private Date date;
    private int moneyAmount;

    public TransactionDate(Date date, int moneyAmount) {
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

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
