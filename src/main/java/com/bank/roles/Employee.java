
package com.bank.roles;

import com.bank.auth.*;

public class Employee extends User {

    public Employee(String id, String username, String password, Role role) {
        super(id, username, password, role);
    }

    // Employee-specific methods
}
