package main.java.observer;

import main.java.util.AppLogger;

/**
 * Concrete Observer implementation that monitors account balances.
 * This service triggers a security warning if an account's balance
 * drops below a predefined safety threshold.
 */
public class AlertService implements AccountObserver {
    private final double threshold;

    public AlertService(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public void update(String accountName, double newBalance) {
        if (newBalance < threshold) {
            AppLogger.getLogger().warning("Account " + accountName + " is below threshold! Current Balance: " + newBalance);
        }
    }
}