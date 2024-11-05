package com.bank.auth;

public class User {
    // TODO: Implement User functionality

public abstract class User {
    private String id;
    private String username;
    private String password;
    private Role role;

    public User(String id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    // Additional methods can be defined here
}

}
