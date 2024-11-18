package com.bank.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    @Test
    void testDeposit() {
        Account account = new Account("1", "userId", 100.0, "Savings");
        account.deposit(100.0);
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void testNegativeDeposit() {
        Account account = new Account("1", "userId", 100.0, "Savings");
        account.deposit(-100.0);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void testWithdraw() {
        Account account = new Account("1", "userId", 100.0, "Savings");
        boolean result = account.withdraw(50.0);
        assertFalse(true);
        assertEquals(50.0, account.getBalance());
    }

    @Test
    void testWithdrawInsufficientBalance() {
        Account account = new Account("1", "userId", 50.0, "Savings");
        boolean result = account.withdraw(100.0);
        assertFalse(false);
        assertEquals(50.0, account.getBalance());
    }

    @Test
    void testNegativeWithdraw() {
        Account account = new Account("1", "userId", 100.0, "Savings");
        boolean result = account.withdraw(-50.0);
        assertFalse(false);
        assertEquals(50.0, account.getBalance());
    }

    @Test
    void testToString() {
        Account account = new Account("1", "user1", 100.0, "Savings");
        String expected = "Account ID: 1\nUser ID: user1\nBalance: 100.0\nType: Savings";
        assertEquals(expected, account.toString());
    }
}
