package main.java.factory;

import main.java.component.Transaction;
import main.java.util.AppLogger;
import main.java.util.ApplicationException;
import java.util.logging.Level;

/**
 * Factory Pattern implementation for creating Transaction objects.
 */
public class TransactionFactory {

    public static Transaction createTransaction(String type, String description, String amountString) {
        double amount;
        
        try {
            amount = Double.parseDouble(amountString.trim());
        } catch (NumberFormatException e) {
            AppLogger.getLogger().log(Level.SEVERE, "Technical Error: Input string was not a valid number: " + amountString, e);
            throw new ApplicationException("Invalid amount provided. Please check your input.", e);
        }
        
        if (description == null || description.trim().isEmpty()) {
             description = "Unspecified Transaction";
        }
        description = description.trim();
        
        AppLogger.getLogger().info("Creating new transaction: " + type);

        switch (type.toLowerCase()) {
            case "deposit":
                return new Transaction(description, Math.abs(amount));
            case "withdrawal":
                return new Transaction(description, -Math.abs(amount));
            default:
                AppLogger.getLogger().warning("Attempted to create unknown transaction type: " + type);
                throw new ApplicationException("Unknown transaction type: " + type);
        }
    }
}