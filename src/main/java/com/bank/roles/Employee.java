package com.bank.roles;

import com.bank.auth.User;
import com.bank.models.LoanApplication;
import com.bank.services.AccountService;
import com.bank.services.LoanService;

import java.util.List;
import java.util.Scanner;

public class Employee extends User {

    private AccountService accountService;
    private LoanService loanService;
    private Scanner scanner = new Scanner(System.in);

    public Employee(String username, String password) {
        super(username, password, "Employee");
        this.accountService = new AccountService();
        this.loanService = new LoanService();
    }
    
    public void listPendingLoans() {
        System.out.println("--- Pending Loan Applications ---");
        List<LoanApplication> pendingLoans = loanService.getPendingLoans(); // Method to retrieve pending loans
        for (LoanApplication loan : pendingLoans) {
            System.out.println("Loan ID: " + loan.getLoanId() + ", Account ID: " + loan.getAccountId() + 
                               ", Amount: " + loan.getAmount() + ", Status: " + loan.getStatus());
        }
    }
    // Open a new account for a client
    public void openAccount() {
        System.out.print("Enter new account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter user ID for the account: ");
        String userId = scanner.nextLine();
        System.out.print("Enter initial deposit amount: ");
        double initialDeposit = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter account type (Savings/Checking): ");
        String type = scanner.nextLine();

        accountService.openAccount(accountNumber, userId, initialDeposit, type);
    }

    // Close an existing account
    public void closeAccount() {
        System.out.print("Enter account number to close: ");
        String accountNumber = scanner.nextLine();

        accountService.closeAccount(accountNumber);
    }

    // Process loan applications by approving or rejecting
    public void processLoanApplication() {
        System.out.print("Enter loan application ID: ");
        String loanId = scanner.nextLine();

        System.out.println("1. Approve Loan");
        System.out.println("2. Reject Loan");
        System.out.print("Choose an option: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                loanService.approveLoan(loanId);
                System.out.println("Loan approved.");
                break;
            case 2:
                loanService.rejectLoan(loanId);
                System.out.println("Loan rejected.");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
}
