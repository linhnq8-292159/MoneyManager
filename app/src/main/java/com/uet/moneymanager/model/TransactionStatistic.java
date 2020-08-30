package com.uet.moneymanager.model;

public class TransactionStatistic {
    public int beginMoneyAmount;
    public int endMoneyAmount;

    public TransactionStatistic(int beginMoneyAmount, int endMoneyAmount) {
        this.beginMoneyAmount = beginMoneyAmount;
        this.endMoneyAmount = endMoneyAmount;
    }

    public int getBeginMoneyAmount() {
        return beginMoneyAmount;
    }

    public void setBeginMoneyAmount(int beginMoneyAmount) {
        this.beginMoneyAmount = beginMoneyAmount;
    }

    public int getEndMoneyAmount() {
        return endMoneyAmount;
    }

    public void setEndMoneyAmount(int endMoneyAmount) {
        this.endMoneyAmount = endMoneyAmount;
    }
}
