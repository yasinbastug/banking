
package com.bank;

import com.bank.models.*;

public class Main {
    public static void main(String[] args) {
        // Get the singleton instance of the Bank
        Bank bank = Bank.getInstance();

        // Create an AccountService instance
        AccountService accountService = new AccountService();

        // Create accounts for the client
        Account savingsAccount = new SavingsAccount("123456", client.getUsername(), 1000);
        Account checkingAccount = new CheckingAccount("654321", client.getUsername(), 500);
        
        // Open accounts in the account service
        accountService.openAccount(savingsAccount);
        accountService.openAccount(checkingAccount);

        // Deposit into savings account
        accountService.deposit("123456", 200);

        // Withdraw from checking account
        accountService.withdraw("654321", 100);

        // Transfer between accounts
        accountService.transfer("123456", "654321", 50);

        // (Optional) Display all users in the bank
        System.out.println("Users in the bank:");
        for (User user : bank.getUsers()) {
            System.out.println(user.getUsername() + " - " + user.getRole().getName());
        }
    }
}
