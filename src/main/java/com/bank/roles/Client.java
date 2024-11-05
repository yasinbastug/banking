package com.bank.roles;


public class Client extends User {

    public Client(String id, String username, String password) {
        super(id, username, password, new Role("Client", "Uses banking services."));
    }

    // Client-specific methods
}
