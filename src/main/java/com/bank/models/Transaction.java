package com.bank.models;

import java.util.Date;

import com.bank.utils.FileUtils;


public abstract class Transaction {
    protected String transactionId;
    protected String accountId;
    protected double amount;
    protected Date date;

    public Transaction(String transactionId, String accountId, double amount) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.date = new Date();
    }

    public abstract void execute();
}