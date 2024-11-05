
package com.bank.roles;

import com.bank.auth.*;

public class Manager extends User {

    public Manager(String id, String username, String password, Role role) {
        super(id, username, password, role);
    }

    // Manager-specific methods
}
