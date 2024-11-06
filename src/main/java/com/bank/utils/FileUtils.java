package com.bank.utils;

import com.bank.models.Account;
import com.bank.models.CheckingAccount;
import com.bank.models.LoanApplication;
import com.bank.models.SavingsAccount;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtils {

    public static String getUserRole(String filePath, String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    return parts[2];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeLine(String filePath, String line, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readAllLines(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * Writes a list of lines to a file, optionally overwriting the entire file or appending.
     *
     * @param filePath The path to the file.
     * @param lines    The list of lines to write.
     * @param append   If true, appends to the file; otherwise, overwrites it.
     */
    public static void writeLines(String filePath, List<String> lines, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            if (!append) {
                // Overwrite mode: Clear the file content before writing
                new PrintWriter(filePath).close();
            }
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Account loadAccount(String accountId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(accountId)) {
                    String userId = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    String type = parts[3];
                    return type.equals("Savings") ? new SavingsAccount(accountId, userId, balance)
                            : new CheckingAccount(accountId, userId, balance);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteAccount(String accountId, String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(accountId + ",")) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String ln : lines) {
                writer.write(ln);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveLoanApplication(LoanApplication loanApplication) {
        String data = loanApplication.getLoanId() + "," + loanApplication.getAccountId() + "," + loanApplication.getAmount()
                + "," + loanApplication.getMonthlyPayment() + "," + loanApplication.getTermInMonths() + ",Pending";
        writeLine("src/main/resources/data/loans.txt", data, true);
    }

    public static void logTransaction(String type, String details, double amount) {
        String filePath = "src/main/resources/data/transactions.txt";
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logEntry = timestamp + "," + type + "," + details + "," + amount;
        writeLine(filePath, logEntry, true);
    }
    
    public static boolean deleteUser(String username, String filePath) {
        List<String> lines = readAllLines(filePath);
        boolean userFound = false;

        List<String> updatedLines = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (!parts[0].equals(username)) {
                updatedLines.add(line);
            } else {
                userFound = true;
            }
        }

        if (userFound) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : updatedLines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return userFound;
    }
}
