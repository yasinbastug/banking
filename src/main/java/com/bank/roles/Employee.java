package com.bank.roles;


public class Employee extends User {

    public Employee(String id, String username, String password) {
        super(id, username, password, new Role("Employee", "Handles customer transactions and services."));
    }

    // Employee-specific methods
}
