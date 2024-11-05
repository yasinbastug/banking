
package com.bank;

import com.bank.models.*;
import com.bank.auth.*;
import com.bank.services.*;
import com.bank.roles.*;

public class Main {
    public static void main(String[] args) {
        // Get the singleton instance of the Bank
        Bank bank = Bank.getInstance();

        // Create roles
        Role clientRole = new Role("Client");
        Role employeeRole = new Role("Employee");
        Role managerRole = new Role("Manager");

        // Create users
        User client = new Client("3", "client1", "password123", clientRole);
        User employee = new Employee("2", "employee1", "password123", employeeRole);
        User manager = new Manager("1", "manager1", "password123", managerRole);

        // Add users to the bank
        bank.addUser(client);
        bank.addUser(employee);
        bank.addUser(manager);

        // Create an AccountService instance
        AccountService accountService = new AccountService();

        // Create accounts for the client
        Account savingsAccount = new SavingsAccount("123456", "client1", 1000);
        Account checkingAccount = new CheckingAccount("654321", "client1", 500);

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
