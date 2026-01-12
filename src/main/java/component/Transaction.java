package main.java.component;

/**
 * The Leaf in the Composite Pattern. Represents a single transaction.
 */
public class Transaction implements FinancialComponent {
    private String description;
    private double amount; // Positive for deposits, negative for withdrawals

    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public void displayDetails() {
        System.out.println("   Transaction: " + description + " (" + (amount >= 0 ? "+" : "") + amount + ")");
    }

    @Override
    public double getBalance() {
        return amount;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toCSV() {
        String type = (amount < 0) ? "WITHDRAWAL" : "DEPOSIT";
        return type + "," + description + "," + amount;
    }
}