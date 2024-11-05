package com.bank.models;

import java.util.Date;

import com.bank.utils.FileUtils;


public abstract class Transaction {
    private String id;
    private Date createdAt;
    private double amount;
    private String accountNumber;
    
    private static final String FILE_PATH = "src/main/resources/data/transactions.txt";

    public Transaction(String id, double amount, String accountNumber) {
        this.id = id;
        this.createdAt = new Date();
        this.amount = amount;
        this.accountNumber = accountNumber;
    }

    public String getId() { return id; }
    public Date getCreatedAt() { return createdAt; }
    public double getAmount() { return amount; }
    public String getAccountNumber() { return accountNumber; }

    public abstract void process();

    // Save transaction to file
    public void saveToFile() {
        String line = id + "," + createdAt.getTime() + "," + amount + "," + accountNumber + "," + getClass().getSimpleName();
        FileUtils.writeLine(FILE_PATH, line, true);
    }
}


