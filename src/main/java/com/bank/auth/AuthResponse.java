package com.bank.auth;

public class AuthResponse {
    private boolean success;
    private String role;

    public AuthResponse(boolean success, String role) {
        this.success = success;
        this.role = role;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getRole() {
        return role;
    }
}
