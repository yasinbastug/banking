package com.bank.models;

public class LoanApplication {
    private String loanId;
    private String accountId;
    private double amount;
    private double outstandingBalance;
    private double monthlyPayment;
    private int termInMonths;
    private String status; // Tracks if loan is 'Pending', 'Approved', or 'Paid'

    public LoanApplication(String loanId, String accountId, double amount, double monthlyPayment, int termInMonths) {
        this.loanId = loanId;
        this.accountId = accountId;
        this.amount = amount;
        this.outstandingBalance = amount; // Initially set to the loan amount
        this.monthlyPayment = monthlyPayment;
        this.termInMonths = termInMonths;
        this.status = "Pending";
    }

    // Additional constructor for loading existing loans
    public LoanApplication(String loanId, String accountId, double amount, double outstandingBalance,
                           double monthlyPayment, int termInMonths, String status) {
        this.loanId = loanId;
        this.accountId = accountId;
        this.amount = amount;
        this.outstandingBalance = outstandingBalance;
        this.monthlyPayment = monthlyPayment;
        this.termInMonths = termInMonths;
        this.status = status;
    }

    // Getters
    public String getLoanId() {
        return loanId;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public double getOutstandingBalance() {
        return outstandingBalance;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public int getTermInMonths() {
        return termInMonths;
    }

    public String getStatus() {
        return status;
    }

    // Allows a repayment on the loan
    public void makeRepayment(double amount) {
        outstandingBalance -= amount;
        if (outstandingBalance <= 0) {
            outstandingBalance = 0;
            status = "Paid";
        }
    }

    // Saves the loan application to file
    public void save() {
        String data = loanId + "," + accountId + "," + amount + "," + outstandingBalance + "," +
                      monthlyPayment + "," + termInMonths + "," + status;
        com.bank.utils.FileUtils.writeLine("src/main/resources/data/loans.txt", data, true);
    }
}
