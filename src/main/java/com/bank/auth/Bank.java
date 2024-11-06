package com.bank.auth;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private static Bank instance; // Singleton instance
    private List<User> users; // List to store users

    // Private constructor to prevent instantiation
    private Bank() {
        users = new ArrayList<>();
    }

    // Method to get the singleton instance
    public static synchronized Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    // Method to add a user
    public void addUser(User user) {
        users.add(user);
    }

    // Method to get all users
    public List<User> getUsers() {
        return new ArrayList<>(users); // Return a copy of the user list
    }

    // Method to find a user by username (or other criteria as needed)
    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // User not found
    }

    // Additional methods for account management can be added here
}
