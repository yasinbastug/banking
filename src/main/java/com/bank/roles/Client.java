package com.bank.roles;

import com.bank.auth.User;
import com.bank.models.Account;
import com.bank.models.LoanApplication;
import com.bank.services.AccountService;
import com.bank.services.LoanService;
import com.bank.services.TransactionService;


import java.util.List;
import java.util.Scanner;

public class Client extends User {

    private AccountService accountService; // Ensure this is initialized
    private TransactionService transactionService;
    private LoanService loanService;
    private Scanner scanner = new Scanner(System.in);

    public Client(String username, String password) {
        super(username, password, "Client");
        this.accountService = new AccountService(); // Initialize AccountService here
        this.loanService = new LoanService();       // Initialize LoanService as well
    }

    // View account details including balance, deposits, and outstanding loan debt
    public void viewAccountDetails() {
        System.out.println("\n--- Account Details ---");

        List<Account> accounts = accountService.getAccountsByUserId(getUsername());
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            for (Account account : accounts) {
                System.out.println("Account ID: " + account.getAccountId());
                System.out.println("Balance: " + account.getBalance());
                System.out.println("Type: " + account.getType());
                System.out.println("------");

                // Retrieve outstanding loans associated with this account ID
                List<LoanApplication> loans = loanService.getOutstandingLoans(account.getAccountId());
                if (loans.isEmpty()) {
                    System.out.println("No outstanding loans.");
                } else {
                    System.out.println("\n--- Outstanding Loans for Account ID " + account.getAccountId() + " ---");
                    for (LoanApplication loan : loans) {
                        System.out.println("Loan ID: " + loan.getLoanId());
                        System.out.println("Original Amount: " + loan.getAmount());
                        System.out.println("Outstanding Balance: " + loan.getOutstandingBalance());
                        System.out.println("Monthly Payment: " + loan.getMonthlyPayment());
                        System.out.println("Term (Months): " + loan.getTermInMonths());
                        System.out.println("------");
                    }
                }
            }
        }
    }



    // Perform a deposit
    public void deposit() {
        System.out.print("Enter account ID for deposit: ");
        String accountId = scanner.nextLine();
        System.out.print("Enter deposit amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        transactionService.deposit(accountId, amount);
    }

    // Perform a withdrawal
    public void withdraw() {
        System.out.print("Enter account ID for withdrawal: ");
        String accountId = scanner.nextLine();
        System.out.print("Enter withdrawal amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        transactionService.withdraw(accountId, amount);
    }

    // Perform a transfer
    public void transfer() {
        System.out.print("Enter your account ID: ");
        String fromAccountId = scanner.nextLine();
        System.out.print("Enter recipient account ID: ");
        String toAccountId = scanner.nextLine();
        System.out.print("Enter transfer amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        transactionService.transfer(fromAccountId, toAccountId, amount);
    }

    // Apply for a loan
    public void applyForLoan() {
        System.out.print("Enter your account ID: ");
        String accountId = scanner.nextLine();
        System.out.print("Enter loan amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter loan term (in months): ");
        int termInMonths = Integer.parseInt(scanner.nextLine());

        loanService.applyForLoan(accountId, amount, termInMonths);
    }
    public void repayLoan() {
        System.out.print("Enter loan ID to repay: ");
        String loanId = scanner.nextLine();
        System.out.print("Enter payment amount: ");
        double paymentAmount = Double.parseDouble(scanner.nextLine());

        loanService.repayLoan(loanId, paymentAmount);
    }
}
