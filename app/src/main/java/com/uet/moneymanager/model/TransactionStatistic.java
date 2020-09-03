package com.uet.moneymanager.model;

public class TransactionStatistic {
    public int initialAmount;
    public int amountOfThisMonth;

    public TransactionStatistic(int initialAmount, int amountOfThisMonth) {
        this.initialAmount = initialAmount;
        this.amountOfThisMonth = amountOfThisMonth;
    }

    public int getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(int initialAmount) {
        this.initialAmount = initialAmount;
    }

    public int getAmountOfThisMonth() {
        return amountOfThisMonth;
    }

    public void setAmountOfThisMonth(int amountOfThisMonth) {
        this.amountOfThisMonth = amountOfThisMonth;
    }
}
