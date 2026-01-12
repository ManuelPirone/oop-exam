package test.java.tests;

import main.java.component.Account;
import main.java.component.Transaction;
import main.java.factory.TransactionFactory;
import main.java.util.ApplicationException;
import main.java.service.StorageService;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the core logic of the application.
 * Focuses on Factory object creation, input sanitization, 
 * and data persistence cycles.
 */
public class TransactionFactoryTest {

    // 1. Test Valid Deposit
    @Test
    public void testCreateDepositSuccess() {
        Transaction t = TransactionFactory.createTransaction("deposit", "Salary", "1000.00");
        
        assertNotNull(t, "Transaction object should not be null");
        assertEquals(1000.00, t.getAmount(), 0.001, "Amount should be positive 1000.00");
        assertEquals("Salary", t.getDescription(), "Description should match input");
    }

    // 2. Test Valid Withdrawal (checking logic converts it to negative)
    @Test
    public void testCreateWithdrawalSuccess() {
        Transaction t = TransactionFactory.createTransaction("withdrawal", "Rent", "500.50");
        
        assertNotNull(t);
        assertEquals(-500.50, t.getAmount(), 0.001, "Withdrawal amount should be negative");
    }

    // 3. Test Security: Input Sanitization (Invalid Number)
    @Test
    public void testInvalidNumberInput() {
        Exception exception = assertThrows(ApplicationException.class, () -> {
            TransactionFactory.createTransaction("deposit", "Bad Input", "not_a_number");
        });

        String expectedMessage = "Invalid amount provided";
        assertTrue(exception.getMessage().contains(expectedMessage), 
            "Exception message should be user-friendly");
    }

    // 4. Test Security: Unknown Transaction Type
    @Test
    public void testInvalidTransactionType() {
        assertThrows(ApplicationException.class, () -> {
            TransactionFactory.createTransaction("investment", "Crypto", "100");
        });
    }

    // 5. Test Sanitization: Null Description
    @Test
    public void testNullDescriptionSanitization() {
        Transaction t = TransactionFactory.createTransaction("deposit", null, "50.00");
        
        assertNotNull(t);
        assertFalse(t.getDescription().isEmpty(), "Description should not be empty/null");
    }

    // 6. Test I/O: Store And Load Data
    @Test
    public void testSaveAndLoadCycle() {
        Account original = new Account("Test Account");
        original.addTransaction(new Transaction("Salary", 1000.0));
        original.addTransaction(new Transaction("Rent", -500.0));
        
        String testFile = "test_storage.csv";
        StorageService.saveAccountData(original, testFile);

        Account loaded = new Account("Fresh Account");
        StorageService.loadAccountData(loaded, testFile);

        assertEquals(original.getBalance(), loaded.getBalance(), 0.001, "Balances must match after reload");
        assertEquals(2, loaded.getTransactions().size(), "Should have 2 transactions after reload");
    }
}