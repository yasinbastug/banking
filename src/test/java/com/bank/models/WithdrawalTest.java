public class WithdrawalTest {
    private FileUtils fileUtilsMock;
    private Withdrawal withdrawal;

    @BeforeEach
    void setup() {
        fileUtilsMock = mock(FileUtils.class);
    }

    @Test
    void testSuccessfulWithdrawal() {
        Account mockAccount = mock(Account.class);
        when(fileUtilsMock.loadAccount("accountId")).thenReturn(mockAccount);
        when(mockAccount.withdraw(100.0)).thenReturn(true); // successful withdrawal
        when(mockAccount.getBalance()).thenReturn(900.0); // return new balance after withdrawal

        withdrawal = new Withdrawal("transac1", "accountId", 100.0);
        withdrawal.execute();

        verify(mockAccount).withdraw(100.0);
        verify(mockAccount).save();
        verify(fileUtilsMock).logTransaction("Withdrawal", "Account: accountId", 100.0);
    }

    @Test
    void testWithdrawalAccountNotFound() {
        when(fileUtilsMock.loadAccount("accountId")).thenReturn(null);

        withdrawal = new Withdrawal("transac2", "accountId", 100.0);
        withdrawal.execute();

        verify(fileUtilsMock, never()).logTransaction(anyString(), anyString(), anyDouble());
    }

    @Test
    void testNegativeWithDrawal() {
        Account mockAccount = mock(Account.class);
        when(fileUtilsMock.loadAccount("accountId")).thenReturn(mockAccount);

        withdrawal = new Withdrawal("transac3", "accountId", -100.0);
        withdrawal.execute();

        verify(mockAccount, never()).withdraw(anyDouble());
        verify(mockAccount, never()).save();
        verify(fileUtilsMock, never()).logTransaction(anyString(), anyString(), anyDouble());
    }
}
