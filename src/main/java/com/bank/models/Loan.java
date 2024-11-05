package com.bank.models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.bank.utils.FileUtils;

public class Loan {
    private String loanId;
    private String type;
    private double loanAmount;
    private double interestRate;
    private Date startDate;
    private int durationInMonths;
    private double balance; // How much needs to be repaid
    private String borrowerId;

    private static final String FILE_PATH = "src/main/resources/data/loans.txt";

    public Loan(String loanId, String type, double loanAmount, double interestRate, Date startDate, int durationInMonths, String borrowerId) {
        this.loanId = loanId;
        this.type = type;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.startDate = startDate;
        this.durationInMonths = durationInMonths;
        this.balance = loanAmount;
        this.borrowerId = borrowerId;
    }

    // Getters and Setters
    public String getLoanId() { return loanId; }
    public double getLoanAmount() { return loanAmount; }
    public double getInterestRate() { return interestRate; }
    public int getDurationInMonths() { return durationInMonths; }
    public double getBalance() { return balance; }
    public String getBorrowerId() { return borrowerId; }

    private void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean makePayment(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            updateLoanInFile();
            return true;
        }
        return false;
    }

    // Apply monthly interest rate
    public void applyInterest() {
        double monthlyInterestRate = (interestRate / 100) / 12;
        balance += balance * monthlyInterestRate;
    }

    public boolean isPaidOff() { return balance <= 0; }

    // Save loan details to file
    public void saveToFile() {
        String line = loanId + "," + type + "," + loanAmount + "," + interestRate + "," + startDate.getTime() + "," + durationInMonths + "," + balance + "," + borrowerId;
        FileUtils.writeLine(getFilePath(), line, true);
    }

    // Method to update loan balance in file
    private void updateLoanInFile() {
        List<String> lines = FileUtils.readAllLines(getFilePath());
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts[0].equals(loanId)) {
                lines.set(i, loanId + "," + type + "," + loanAmount + "," + interestRate + "," + startDate.getTime() + "," + durationInMonths + "," + balance + "," + borrowerId);
                break;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath()))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Loan loadLoan(String loanId) {
        List<String> lines = FileUtils.readAllLines(getFilePath());
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts[0].equals(loanId)) {
                String type = parts[1];
                double loanAmount = Double.parseDouble(parts[2]);
                double interestRate = Double.parseDouble(parts[3]);
                Date startDate = new Date(Long.parseLong(parts[4]));
                int durationInMonths = Integer.parseInt(parts[5]);
                double balance = Double.parseDouble(parts[6]);
                String borrowerId = parts[7];
                Loan loan = new Loan(loanId, type, loanAmount, interestRate, startDate, durationInMonths, borrowerId);
                loan.setBalance(balance);
                return loan;
            }
        }
        return null;
    }

    public static String getFilePath() {
        return FILE_PATH;
    }
}
