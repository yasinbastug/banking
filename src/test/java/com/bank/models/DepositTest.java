package com.bank.models;

import com.bank.utils.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DepositTest {
    private FileUtils fileUtilsMock;
    private Deposit deposit;

    @BeforeEach
    void setUp() {
        fileUtilsMock = mock(FileUtils.class);
    }

    @Test
    void testSuccessfulDeposit() {
        Account mockAccount = mock(Account.class);
        when(fileUtilsMock.loadAccount("accountId")).thenReturn(mockAccount);

        deposit = new Deposit("transac1", "accountId", 100.0, fileUtilsMock);
        deposit.execute();

        verify(mockAccount).deposit(100.0);
        verify(mockAccount).save();
        verify(fileUtilsMock).logTransaction("Deposit", "Account: accountId", 100.0);
    }

    @Test
    void testAccountNotFound() {
        when(fileUtilsMock.loadAccount("accountId")).thenReturn(null);

        deposit = new Deposit("transac1", "accountId", 100.0, fileUtilsMock);
        deposit.execute();

        verify(fileUtilsMock, never()).logTransaction(anyString(), anyString(), anyDouble());
    }

    @Test
    void testNegativeDeposit() {
        Account mockAccount = mock(Account.class);
        when(fileUtilsMock.loadAccount("accountId")).thenReturn(mockAccount);

        deposit = new Deposit("transac1", "accountId", -50.0, fileUtilsMock);
        deposit.execute();
        
        verify(mockAccount, never()).deposit(anyDouble());
        verify(mockAccount, never()).save();
        verify(fileUtilsMock, never()).logTransaction(anyString(), anyString(), anyDouble());
    }
}
