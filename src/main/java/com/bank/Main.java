package com.bank;
import com.bank.models.*;

public class Main {
    public static void main(String[] args) {
        AccountService accountService = new AccountService();

        // Create accounts
        Account savingsAccount = new SavingsAccount("123456", "user1", 1000);
        Account checkingAccount = new CheckingAccount("654321", "user1", 500);
        accountService.openAccount(savingsAccount);
        accountService.openAccount(checkingAccount);

        // Deposit into savings account
        accountService.deposit("123456", 200);

        // Withdraw from checking account
        accountService.withdraw("654321", 100);

        // Transfer between accounts
        accountService.transfer("123456", "654321", 50);
    }
}
