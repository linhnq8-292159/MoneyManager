package com.uet.moneymanager.model;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private int id;
    private int amount;
    private TransactionGroup transactionGroup;
    private String note;
    private Date date;

    public Transaction() {
        this.amount = amount;
        this.transactionGroup = transactionGroup;
        this.note = note;
        this.date = date;
    }

    public Transaction(int id, int amount, TransactionGroup transactionGroup, String note, Date date) {
        this.id = id;
        this.amount = amount;
        this.transactionGroup = transactionGroup;
        this.note = note;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public TransactionGroup getTransactionGroup() {
        return transactionGroup;
    }

    public void setTransactionGroup(TransactionGroup transactionGroup) {
        this.transactionGroup = transactionGroup;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
