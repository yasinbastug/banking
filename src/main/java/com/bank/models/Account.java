package com.bank.models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.bank.utils.FileUtils;

public abstract class Account {
    private String accountNumber;
    private String type;
    private String userId;
    private double balance;

    private static final String FILE_PATH = "src/main/resources/data/accounts.txt";

    public Account(String accountNumber, String type, String userId, double initialBalance) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.userId = userId;
        this.balance = initialBalance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getType() { return type; }
    public String getUserId() { return userId; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            updateAccountInFile();
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            updateAccountInFile();
            return true;
        }
        return false;
    }

    // Save account to file
    public void saveToFile() {
        String line = accountNumber + "," + type + "," + userId + "," + balance;
        FileUtils.writeLine(FILE_PATH, line, true);
    }

    // Update account balance in file
    private void updateAccountInFile() {
        List<String> lines = FileUtils.readAllLines(FILE_PATH);
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts[0].equals(accountNumber)) {
                lines.set(i, accountNumber + "," + type + "," + userId + "," + balance);
                break;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load account by accountNumber
    public static Account loadAccount(String accountNumber) {
        List<String> lines = FileUtils.readAllLines(FILE_PATH);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts[0].equals(accountNumber)) {
                String type = parts[1];
                String userId = parts[2];
                double balance = Double.parseDouble(parts[3]);
                if (type.equals("Savings")) {
                    return new SavingsAccount(accountNumber, userId, balance);
                } else if (type.equals("Checking")) {
                    return new CheckingAccount(accountNumber, userId, balance);
                }
            }
        }
        return null;
    }
}
