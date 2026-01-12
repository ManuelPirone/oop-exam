package main.java.observer;

/**
 * The Observer interface. Any class that needs to react to account 
 * changes (like alerts or logs) will implement this.
 */
public interface AccountObserver {
    void update(String accountName, double newBalance);
}