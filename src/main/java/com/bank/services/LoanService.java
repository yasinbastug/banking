package com.bank.services;

import com.bank.models.LoanApplication;
import com.bank.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class LoanService {

    private static final String LOANS_FILE_PATH = "src/main/resources/data/loans.txt";

    public void applyForLoan(String accountId, double amount, int termInMonths) {
        String loanId = "LN" + System.currentTimeMillis(); // Generate a unique loan ID
        double monthlyPayment = calculateMonthlyPayment(amount, 0.05, termInMonths);
        LoanApplication loanApplication = new LoanApplication(loanId, accountId, amount, monthlyPayment, termInMonths);

        loanApplication.save(); // Save the loan application to file
        System.out.println("Loan application submitted with Loan ID: " + loanId);
        System.out.println("Monthly Payment: " + monthlyPayment);
    }
    
    public List<LoanApplication> getPendingLoans() {
        List<String> lines = FileUtils.readAllLines(LOANS_FILE_PATH);
        List<LoanApplication> pendingLoans = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length < 7) {
                System.out.println("Skipping malformed line in loans file: " + line);
                continue;
            }

            String status = parts[6];
            if (status.equals("Pending")) {
                LoanApplication loan = new LoanApplication(parts[0], parts[1], Double.parseDouble(parts[2]),
                        Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), Integer.parseInt(parts[5]), status);
                pendingLoans.add(loan);
            }
        }
        return pendingLoans;
    }


    // Approve a loan application
    public void approveLoan(String loanId) {
        updateLoanStatus(loanId, "Approved");
    }

    // Reject a loan application
    public void rejectLoan(String loanId) {
        updateLoanStatus(loanId, "Rejected");
    }

    // Helper method to update the loan status
    private void updateLoanStatus(String loanId, String newStatus) {
        List<String> lines = FileUtils.readAllLines(LOANS_FILE_PATH);
        boolean loanFound = false;

        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length < 7) {
                System.out.println("Skipping malformed line in loans file: " + lines.get(i));
                continue;
            }

            if (parts[0].equals(loanId)) {
                parts[6] = newStatus; // Update the status field
                lines.set(i, String.join(",", parts)); // Update the line with the new status
                loanFound = true;
                break;
            }
        }

        if (loanFound) {
            FileUtils.writeLines(LOANS_FILE_PATH, lines, false); // Overwrite the file with the updated status
            System.out.println("Loan " + loanId + " has been " + newStatus + ".");
        } else {
            System.out.println("Loan ID " + loanId + " not found.");
        }
    }
    
    public void repayLoan(String loanId, double paymentAmount) {
        List<String> lines = FileUtils.readAllLines(LOANS_FILE_PATH);
        boolean loanFound = false;

        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length < 7) {
                System.out.println("Skipping malformed line in loans file: " + lines.get(i));
                continue;
            }

            if (parts[0].equals(loanId)) {
                double outstandingBalance = Double.parseDouble(parts[3]);
                String status = parts[6];

                // Ensure loan is approved and not already paid
                if (!status.equals("Approved")) {
                    System.out.println("Loan is not approved or has already been paid.");
                    return;
                }

                // Deduct the payment from the outstanding balance
                outstandingBalance -= paymentAmount;
                if (outstandingBalance <= 0) {
                    outstandingBalance = 0;
                    parts[6] = "Paid"; // Update status to "Paid" if loan is fully repaid
                    System.out.println("Loan fully repaid.");
                } else {
                    System.out.println("Payment successful. Remaining balance: " + outstandingBalance);
                }

                parts[3] = String.valueOf(outstandingBalance); // Update outstanding balance in the line
                lines.set(i, String.join(",", parts)); // Update the line with the new balance and status
                loanFound = true;
                break;
            }
        }

        if (loanFound) {
            FileUtils.writeLines(LOANS_FILE_PATH, lines, false); // Overwrite the file with the updated loan data
        } else {
            System.out.println("Loan ID " + loanId + " not found.");
        }
    }

    private double calculateMonthlyPayment(double principal, double annualRate, int termInMonths) {
        double monthlyRate = annualRate / 12;
        return principal * monthlyRate / (1 - Math.pow(1 + monthlyRate, -termInMonths));
    }

    // Retrieve outstanding loans for a client
    public List<LoanApplication> getOutstandingLoans(String accountId) {
        List<String> lines = FileUtils.readAllLines(LOANS_FILE_PATH);
        List<LoanApplication> outstandingLoans = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length < 7) {
                System.out.println("Skipping malformed line in loans file: " + line);
                continue;
            }

            String loanAccountId = parts[1]; // This is the account ID associated with the loan
            double outstandingBalance = Double.parseDouble(parts[3]);
            String status = parts[6];

            // Ensure loan is approved and has an outstanding balance
            if (loanAccountId.equals(accountId) && outstandingBalance > 0 && status.equals("Approved")) {
                double originalAmount = Double.parseDouble(parts[2]);
                double monthlyPayment = Double.parseDouble(parts[4]);
                int termInMonths = Integer.parseInt(parts[5]);

                LoanApplication loan = new LoanApplication(parts[0], loanAccountId, originalAmount, outstandingBalance,
                                                           monthlyPayment, termInMonths, status);
                outstandingLoans.add(loan);
            }
        }
        return outstandingLoans;
    }

}
