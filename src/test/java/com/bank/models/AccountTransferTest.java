package com.bank.models;

import com.bank.utils.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AccountTransferTest {

    @BeforeEach
    void setUp() {
        // Reset the FileUtils mocks before each test
        FileUtils.resetMocks();
    }

    @Test
    void testExecuteTransferSuccess() {
        Account sender = mock(Account.class);
        Account receiver = mock(Account.class);

        when(FileUtils.loadAccount("senderId")).thenReturn(sender);
        when(FileUtils.loadAccount("receiverId")).thenReturn(receiver);
        when(sender.withdraw(50.0)).thenReturn(true);

        AccountTransfer transfer = new AccountTransfer("transac1", "senderId", 50.0, "receiverId");
        transfer.execute();

        verify(sender).withdraw(50.0);
        verify(receiver).deposit(50.0);
        verify(sender).save();
        verify(receiver).save();
        verify(FileUtils.class);
        FileUtils.logTransaction(eq("Transfer"), contains("From: senderId To: receiverId"), eq(50.0));
    }

    @Test
    void testExecuteInsufficientFunds() {
        Account sender = mock(Account.class);
        Account receiver = mock(Account.class);

        when(FileUtils.loadAccount("senderId")).thenReturn(sender);
        when(FileUtils.loadAccount("receiverId")).thenReturn(receiver);
        when(sender.withdraw(100.0)).thenReturn(false); // Insufficient funds for withdrawal

        AccountTransfer transfer = new AccountTransfer("transac2", "senderId", 100.0, "receiverId");
        transfer.execute();

        verify(sender).withdraw(100.0);
        verify(receiver, never()).deposit(anyDouble()); // Receiver's deposit shouldn't be called
        verify(FileUtils.class);
        FileUtils.logTransaction(any(), any(), anyDouble()); // Log should not be invoked
    }

    @Test
    void testExecuteAccountNotFound() {
        when(FileUtils.loadAccount("senderId")).thenReturn(null); // Sender account not found

        AccountTransfer transfer = new AccountTransfer("transac3", "senderId", 50.0, "receiverId");
        transfer.execute();

        verify(FileUtils.class);
        FileUtils.loadAccount("senderId"); // Verify FileUtils tried to load the account
        verify(FileUtils.loadAccount("receiverId"), times(1)); // Still checks for the receiver
    }
}