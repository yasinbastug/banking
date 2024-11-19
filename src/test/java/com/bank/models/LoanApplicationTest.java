import com.bank.models.LoanApplication;
import com.bank.utils.FileUtils;

public class LoanApplicationTest {
    @Test
    void testMakePartialRepayment() {
        LoanApplication loanApp = new LoanApplication("loan1", "account1", 10000.0, 200.0, 60);
        loanApp.makeRepayment(2000.0);
        assertEquals(8000.0, loanApp.getOutstandingBalance());
        assertEquals("Pending", loanApp.getStatus());
    }

    @Test
    void testMakeFullRepayment() {
        LoanApplication loanApp = new LoanApplication("loan1", "account1", 10000.0, 200.0, 60);
        loanApp.makeRepayment(10000.0);
        assertEquals(0.0, loanApp.getOutstandingBalance());
        assertEquals("Paid", loanApp.getStatus());
    }

    @Test
    void testRepaymentExceedingBalance() {
        LoanApplication loanApp = new LoanApplication("loan1", "account1", 10000.0, 200.0, 60);
        loanApp.makeRepayment(15000.0);
        assertEquals(0.0, loanApp.getOutstandingBalance());
        assertEquals("Paid", loanApp.getStatus());
    }

    @Test
    void testSaveLoanApplicationToFile() {
        FileUtils fileUtilsMock = mock(FileUtils.class);
        LoanApplication loanApp = new LoanApplication("loan1", "account1", 10000.0, 200.0, 60);
        String expectedData = "loan1,account1,10000.0,5000.0,200.0,60,Pending";

        loanApp.makeRepayment(5000.0);
        loanApp.save();
        assertTrue(fileUtilsMock.containsLine(expectedData));
    }

}
