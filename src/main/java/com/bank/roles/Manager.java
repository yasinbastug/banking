package com.bank.roles;


public class Manager extends User {

    public Manager(String id, String username, String password) {
        super(id, username, password, new Role("Manager", "Responsible for managing employees and operations."));
    }

    // Manager-specific methods
}

