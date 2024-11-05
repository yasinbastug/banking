package com.bank.services;

import java.util.HashMap;
import java.util.Map;

import com.bank.models.*;

public class AccountService {

    public void openAccount(Account account) {
        account.saveToFile();
    }

    public void closeAccount(String accountNumber) {
        Account account = Account.loadAccount(accountNumber);
        if (account != null && account.getBalance() == 0) {
            List<String> lines = FileUtils.readAllLines(Account.FILE_PATH);
            lines.removeIf(line -> line.startsWith(accountNumber));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Account.FILE_PATH))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Account must have zero balance to be closed.");
        }
    }

    public void deposit(String accountNumber, double amount) {
        Deposit deposit = new Deposit(generateTransactionId(), amount, accountNumber);
        deposit.process();
    }

    public void withdraw(String accountNumber, double amount) {
        Withdrawal withdrawal = new Withdrawal(generateTransactionId(), amount, accountNumber);
        withdrawal.process();
    }

    public void transfer(String fromAccount, String toAccount, double amount) {
        AccountTransfer transfer = new AccountTransfer(generateTransactionId(), amount, fromAccount, toAccount);
        transfer.process();
    }

    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }
}

