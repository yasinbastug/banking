package com.bank.services;

import java.util.ArrayList;
import java.util.List;

import com.bank.models.Account;
import com.bank.models.CheckingAccount;
import com.bank.models.SavingsAccount;
import com.bank.utils.FileUtils;

public class AccountService {

    private static final String ACCOUNTS_FILE_PATH = "src/main/resources/data/accounts.txt";

    public List<Account> getAccountsByUserId(String userId) {
        List<String> lines = FileUtils.readAllLines(ACCOUNTS_FILE_PATH);
        List<Account> userAccounts = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(",");
            // Ensure line has at least 4 parts: accountId, userId, balance, and type
            if (parts.length < 4) {
                System.out.println("Skipping malformed line in accounts file: " + line);
                continue; // Skip this line if it's malformed
            }

            String accountUserId = parts[1];
            if (accountUserId.equals(userId)) {
                String accountId = parts[0];
                double balance = Double.parseDouble(parts[2]);
                String type = parts[3];
                Account account = new Account(accountId, userId, balance, type);
                userAccounts.add(account);
            }
        }
        return userAccounts;
    }
    
    public void openAccount(String accountNumber, String userId, double initialDeposit, String type) {
        Account account;
        if (type.equalsIgnoreCase("Savings")) {
            account = new SavingsAccount(accountNumber, userId, initialDeposit);
        } else if (type.equalsIgnoreCase("Checking")) {
            account = new CheckingAccount(accountNumber, userId, initialDeposit);
        } else {
            System.out.println("Invalid account type.");
            return;
        }
        account.save(); // Save account to file
        System.out.println("Account successfully created: " + accountNumber);
    }

    public void closeAccount(String accountNumber) {
        Account account = FileUtils.loadAccount(accountNumber);
        if (account != null && account.getBalance() == 0) {
            FileUtils.deleteAccount(accountNumber, ACCOUNTS_FILE_PATH);
            System.out.println("Account " + accountNumber + " has been closed.");
        } else {
            System.out.println("Account must have a zero balance to be closed.");
        }
    }

    public Account getAccount(String accountNumber) {
        return FileUtils.loadAccount(accountNumber);
    }
}
