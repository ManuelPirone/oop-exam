package main.java.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import main.java.component.Account;
import main.java.component.FinancialComponent;
import main.java.component.Transaction;
import main.java.factory.TransactionFactory;
import main.java.util.AppLogger;
import main.java.util.ApplicationException;
import java.util.logging.Level;

/**
 * Service class responsible for data persistence.
 * Handles the serialization of Account transactions to CSV format and
 * deserialization from local storage.
 */
public class StorageService {
    public static void saveAccountData(Account account, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (FinancialComponent fc : account.getTransactions()) {
                 if (fc instanceof Transaction) {
                    writer.write(fc.toCSV());
                    writer.newLine();
                }
            }
            AppLogger.getLogger().info("Successfully saved account data to " + filename);

        } catch (IOException e) {
            AppLogger.getLogger().log(Level.SEVERE, "IO Error during save operation", e);
            throw new ApplicationException("Could not save data. Please check file permissions.", e);
        }
    }

    public static void loadAccountData(Account account, String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            AppLogger.getLogger().info("No data file found. Starting with empty account.");
            return; 
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
             String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                     Transaction t = TransactionFactory.createTransaction(parts[0], parts[1], parts[2]);
                     account.addTransaction(t);
                }
            }
            AppLogger.getLogger().info("Successfully loaded data from " + filename);

        } catch (IOException e) {
            AppLogger.getLogger().log(Level.SEVERE, "IO Error during load operation", e);
            throw new ApplicationException("Could not load data. File might be corrupted.", e);
        }
    }
}
