package com.bank;

import com.bank.auth.AuthService;
import com.bank.auth.User;
import com.bank.roles.Client;
import com.bank.roles.Employee;
import com.bank.roles.Manager;
import com.bank.utils.MenuHandler;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AuthService authService = new AuthService();

    public static void main(String[] args) {
        // Example usage
        Manager manager = new Manager();
        Employee employee = new Employee();
        Client client = new Client();

        // Call these methods as appropriate
        MenuHandler.managerMenu(manager); // Call from MenuHandler
        MenuHandler.employeeMenu(employee); // Call from MenuHandler
        MenuHandler.clientMenu(client); // Call from MenuHandler
    }
}
