package com.bank.auth;

import com.bank.roles.*;
import com.bank.utils.FileUtils;
import java.io.File;

public class AuthService {
    private static final String USERS_FILE_PATH = "src/main/resources/data/users.txt";

    public AuthService() {
        initializeDefaultUsers();
    }

    public User authenticate(String username, String password) {
        String role = FileUtils.getUserRole(USERS_FILE_PATH, username, password);
        if (role != null) {
            switch (role) {
                case "Client":
                    return new Client(username, password);
                case "Employee":
                    return new Employee(username, password);
                case "Manager":
                    return new Manager(username, password);
            }
        }
        return null; // Return null if authentication fails
    }

    private void initializeDefaultUsers() {
        File userFile = new File(USERS_FILE_PATH);
        if (!userFile.exists() || FileUtils.readAllLines(USERS_FILE_PATH).isEmpty()) {
            System.out.println("Creating default users...");
            FileUtils.writeLine(USERS_FILE_PATH, "manager,manager123,Manager", true);
            FileUtils.writeLine(USERS_FILE_PATH, "employee,employee123,Employee", true);
            FileUtils.writeLine(USERS_FILE_PATH, "client,client123,Client", true);
        }
    }
}
