package com.bank.roles;

import com.bank.auth.User;
import com.bank.utils.FileUtils;

import java.util.Scanner;

public class Manager extends User {

    private static final String INTEREST_RATES_FILE = "src/main/resources/data/interest_rates.txt";
    private static final String EMPLOYEES_FILE = "src/main/resources/data/employees.txt";
    private Scanner scanner = new Scanner(System.in);

    public Manager(String username, String password) {
        super(username, password, "Manager");
    }

    // Set interest rates for different account types (e.g., Savings, Checking)
    public void setInterestRates() {
        System.out.println("Set Interest Rates for Account Types:");
        System.out.print("Enter interest rate for Savings Account (e.g., 0.03 for 3%): ");
        double savingsRate = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter interest rate for Checking Account (e.g., 0.01 for 1%): ");
        double checkingRate = Double.parseDouble(scanner.nextLine());

        String data = "Savings," + savingsRate + "\nChecking," + checkingRate;
        FileUtils.writeLine(INTEREST_RATES_FILE, data, false); // Overwrite file with new rates
        System.out.println("Interest rates updated successfully.");
    }

    // Manage employees: add or remove employees from the system
    public void manageEmployees() {
        System.out.println("Employee Management:");
        System.out.println("1. Add Employee");
        System.out.println("2. Remove Employee");
        System.out.print("Choose an option: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                addEmployee();
                break;
            case 2:
                removeEmployee();
                break;
            default:
                System.out.println("Invalid option. Returning to menu.");
        }
    }

    private void addEmployee() {
        System.out.print("Enter new employee's username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password for new employee: ");
        String password = scanner.nextLine();

        String employeeData = username + "," + password + ",Employee";
        FileUtils.writeLine(EMPLOYEES_FILE, employeeData, true); // Append new employee to file
        System.out.println("Employee added successfully.");
    }

    private void removeEmployee() {
        System.out.print("Enter username of employee to remove: ");
        String username = scanner.nextLine();

        // Remove employee from the file
        if (FileUtils.deleteUser(username, EMPLOYEES_FILE)) {
            System.out.println("Employee removed successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }
}
