package com.bank.services;

import com.bank.models.Account;
import com.bank.models.Deposit;
import com.bank.models.Withdrawal;
import com.bank.models.AccountTransfer;
import com.bank.utils.FileUtils;

public class TransactionService {

    public void deposit(String accountNumber, double amount) {
        Account account = FileUtils.loadAccount(accountNumber);
        if (account != null) {
            Deposit deposit = new Deposit("TXN" + System.currentTimeMillis(), accountNumber, amount);
            deposit.execute();
            System.out.println("Deposit successful. New balance: " + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = FileUtils.loadAccount(accountNumber);
        if (account != null) {
            Withdrawal withdrawal = new Withdrawal("TXN" + System.currentTimeMillis(), accountNumber, amount);
            withdrawal.execute();
            System.out.println("Withdrawal successful. New balance: " + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        AccountTransfer transfer = new AccountTransfer("TXN" + System.currentTimeMillis(), fromAccountNumber, amount, toAccountNumber);
        transfer.execute();
    }
}
