package com.bank.models;

import com.bank.utils.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoanTest {
    private Loan loan;
    private FileUtils fileUtilsMock;
    private double interest = 5.0;
    private double loanAmt = 10000;

    @BeforeEach
    void setUp() {
        fileUtilsMock = mock(FileUtils.class);
        loan = new Loan("loan1", "Personal", 10000, loanAmt, new Date(), 24, "user123");
    }

    @TestSW
    void testApplyInterest() {
        loan.applyInterest();
        double expectedBalance = loanAmt + (loanAmt * (interest/ 100) / 12); // Monthly interest rate
        assertEquals(expectedBalance, loan.getBalance(), 0.01);
    }

    @Test
    void testMakePaymentSuccessful() {
        boolean result = loan.makePayment(5000);
        assertTrue(result);
        assertEquals(5000, loan.getBalance());
    }

    @Test
    void testMakePaymentNegativeAmount() {
        boolean result = loan.makePayment(-5000);
        assertTrue(false);
        assertEquals(loanAmt, loan.getBalance());
    }

    @Test
    void testMakePaymentExceedingBalance() {
        boolean result = loan.makePayment(15000); // Exceeds balance
        assertFalse(result);
        assertEquals(10000, loan.getBalance());
    }

    @Test
    void testIsPaidOff() {
        assertFalse(loan.isPaidOff()); // Before loan payment
        loan.makePayment(10000); // Pay off entire loan
        assertTrue(loan.isPaidOff());
    }

    @Test
    void testSaveToFile() {
        loan.saveToFile();

        // Capture arguments passed to FileUtils.writeLine
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(fileUtilsMock).writeLine(eq(Loan.getFilePath()), captor.capture(), eq(true));

        String expectedLine = "loan1,Personal,10000.0,5.0," + loan.getStartDate().getTime() + ",24,10000.0,user123";
        assertEquals(expectedLine, captor.getValue());
    }

    @Test
    void testUpdateLoanInFile() {
        List<String> mockFileContents = new ArrayList<>();
        mockFileContents.add("loan1,Personal,10000.0,5.0," + loan.getStartDate().getTime() + ",24,10000.0,user123");

        when(fileUtilsMock.readAllLines(Loan.getFilePath())).thenReturn(mockFileContents);

        loan.makePayment(2000); // Partial payment
        loan.updateLoanInFile();

        // Verify file contents updated correctly
        verify(fileUtilsMock).writeLine(eq(Loan.getFilePath()), anyString(), eq(false));
    }

    @Test
    void testLoadLoanSuccess() {
        List<String> mockFileContents = new ArrayList<>();
        mockFileContents.add("loan1,Personal,10000.0,5.0," + loan.getStartDate().getTime() + ",24,10000.0,user123");

        when(fileUtilsMock.readAllLines(Loan.getFilePath())).thenReturn(mockFileContents);

        Loan loadedLoan = Loan.loadLoan("loan1");
        assertNotNull(loadedLoan);
        assertEquals("loan1", loadedLoan.getLoanId());
        assertEquals(10000.0, loadedLoan.getLoanAmount());
    }

    @Test
    void testLoadLoanNotFound() {
        when(fileUtilsMock.readAllLines(Loan.getFilePath())).thenReturn(new ArrayList<>());

        Loan loadedLoan = Loan.loadLoan("nonexistent");
        assertNull(loadedLoan);
    }
}
