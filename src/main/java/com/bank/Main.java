package com.bank;

import com.bank.auth.AuthService;
import com.bank.auth.User;
import com.bank.roles.Client;
import com.bank.roles.Employee;
import com.bank.roles.Manager;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AuthService authService = new AuthService();

    public static void main(String[] args) {
        System.out.println("Welcome to the Advanced Bank Account Management System");

        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            User user = authService.authenticate(username, password);
            if (user == null) {
                System.out.println("Invalid credentials, please try again.");
                continue;
            }

            System.out.println("Login successful! Role: " + user.getRole());

            if (user instanceof Manager) {
                managerMenu((Manager) user);
            } else if (user instanceof Employee) {
                employeeMenu((Employee) user);
            } else if (user instanceof Client) {
                clientMenu((Client) user);
            }
        }
    }

    private static void managerMenu(Manager manager) {
        while (true) {
            System.out.println("\n--- Manager Menu ---");
            System.out.println("1. Set Interest Rates");
            System.out.println("2. Manage Employees");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    manager.setInterestRates();
                    break;
                case 2:
                    manager.manageEmployees();
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void employeeMenu(Employee employee) {
        while (true) {
            System.out.println("\n--- Employee Menu ---");
            System.out.println("1. Open Account");
            System.out.println("2. Close Account");
            System.out.println("3. List Pending Loan Applications"); // New option
            System.out.println("4. Process Loan Applications");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    employee.openAccount();
                    break;
                case 2:
                    employee.closeAccount();
                    break;
                case 3:
                    employee.listPendingLoans(); // Call to list pending loans
                    break;
                case 4:
                	employee.processLoanApplication();
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }


    private static void clientMenu(Client client) {
        while (true) {
            System.out.println("\n--- Client Menu ---");
            System.out.println("1. View Account Details");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Apply for Loan");
            System.out.println("6. Repay Loan");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    client.viewAccountDetails();
                    break;
                case 2:
                    client.deposit();
                    break;
                case 3:
                    client.withdraw();
                    break;
                case 4:
                    client.transfer();
                    break;
                case 5:
                    client.applyForLoan();
                    break;
                case 6:
                    client.repayLoan();
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }


}
