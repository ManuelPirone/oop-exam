package main.java.app;

import java.util.logging.Level;

import main.java.component.Account;
import main.java.component.Portfolio;
import main.java.component.Transaction;
import main.java.factory.TransactionFactory;
import main.java.iterator.CustomIterator;
import main.java.observer.AlertService;
import main.java.util.AppLogger;
import main.java.util.ApplicationException;
import main.java.service.StorageService;

public class Main {

    public static void main(String[] args) {
        java.util.Locale.setDefault(java.util.Locale.ENGLISH);

        Portfolio myPortfolio = new Portfolio("Main Financials");
        Account checking = new Account("Everyday Checking");
        Account savings = new Account("Emergency Savings");

        try {
            myPortfolio.addAccount(checking);
            myPortfolio.addAccount(savings);

            System.out.println("\n--- Attempting to Load Existing Data ---");
            StorageService.loadAccountData(checking, "checking_backup.csv");
            
            System.out.println("Current Balance after Load: $" + checking.getBalance());
            
            if (checking.getTransactions().isEmpty()) {
                Transaction t1 = TransactionFactory.createTransaction("deposit", "Paycheck", "3000.00");
                Transaction t2 = TransactionFactory.createTransaction("withdrawal", "Rent Payment", "1200.00");
                Transaction t3 = TransactionFactory.createTransaction("deposit", "Tax Refund", "500.00");
                Transaction t4 = TransactionFactory.createTransaction("withdrawal", "Shopping", "150.00");
                // This transaction triggers the alert
                Transaction expensiveRent = TransactionFactory.createTransaction("withdrawal", "Rent 2", "1500.00");

                checking.addTransaction(t1);
                checking.addTransaction(t2);
                checking.addTransaction(t3);
                checking.addTransaction(t4);
                checking.addTransaction(expensiveRent);
            }
            if (savings.getTransactions().isEmpty()) {
                Transaction t5 = TransactionFactory.createTransaction("deposit", "Savings 1", "200.00");
                savings.addTransaction(t5);
            }

            AlertService securityAlerts = new AlertService(1000.00);
            checking.addObserver(securityAlerts);

            myPortfolio.displayDetails();
            
            CustomIterator<Transaction> withdrawalIterator = checking.createWithdrawalIterator();
            while (withdrawalIterator.hasNext()) {
                Transaction t = withdrawalIterator.next();
                System.out.println("  Found Withdrawal: " + t.getDescription() + " (" + t.getAmount() + ")");
            }
            
            // System.out.println("-- Testing Exception Shielding --");
            // TransactionFactory.createTransaction("deposit", "Bonus", "five hundred");

            System.out.println("\n--- Saving Portfolio Data ---");
            StorageService.saveAccountData(checking, "checking_backup.csv");
            
        } catch (ApplicationException e) {
            System.err.println("APPLICATION ERROR: " + e.getMessage());
            AppLogger.getLogger().log(Level.SEVERE, "APPLICATION ERROR LOG", e);
        }

    }
}